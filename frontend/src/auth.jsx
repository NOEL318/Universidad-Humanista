import { createContext, useContext, useEffect, useState } from "react"
import { api, setUnauthorizedHandler } from "@/lib/api"

const AuthContext = createContext(null)

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null)
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    setUnauthorizedHandler(() => setUser(null))
    api("/auth/me")
      .then(setUser)
      .catch(() => setUser(null))
      .finally(() => setLoading(false))
  }, [])

  const login = async (email, password) => {
    setUser(await api("/auth/login", { method: "POST", body: { email, password } }))
  }

  const logout = async () => {
    await api("/auth/logout", { method: "POST" }).catch(() => {})
    setUser(null)
  }

  return <AuthContext.Provider value={{ user, loading, login, logout }}>{children}</AuthContext.Provider>
}

export function useAuth() {
  return useContext(AuthContext)
}
