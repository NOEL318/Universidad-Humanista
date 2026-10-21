import { useState } from "react"
import { Navigate } from "react-router-dom"
import { toast } from "sonner"
import { useAuth } from "@/auth"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"

export default function Login() {
  const { user, login } = useAuth()
  const [email, setEmail] = useState("")
  const [password, setPassword] = useState("")
  const [loading, setLoading] = useState(false)

  if (user) return <Navigate to="/" replace />

  const submit = async (e) => {
    e.preventDefault()
    setLoading(true)
    try {
      await login(email, password)
    } catch (err) {
      toast.error(err.message)
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="flex min-h-svh flex-col items-center justify-center bg-white px-4">
      <div className="w-full max-w-sm text-center">
        <h1 className="text-4xl font-semibold tracking-tight">Intranet</h1>
        <p className="mt-2 text-lg text-muted-foreground">Universidad Humanista</p>
        <form onSubmit={submit} className="mt-10 grid gap-3">
          <Input
            type="email"
            placeholder="Correo"
            autoComplete="username"
            required
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            className="h-12 rounded-xl px-4 text-base"
          />
          <Input
            type="password"
            placeholder="Contraseña"
            autoComplete="current-password"
            required
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            className="h-12 rounded-xl px-4 text-base"
          />
          <Button type="submit" disabled={loading} className="mt-3 h-12 rounded-full text-base">
            {loading ? "Entrando…" : "Iniciar sesión"}
          </Button>
        </form>
      </div>
    </div>
  )
}
