let onUnauthorized = () => {}

export function setUnauthorizedHandler(fn) {
  onUnauthorized = fn
}

function errorMessage(status, data) {
  if (data.errors) return Object.values(data.errors)[0]
  if (status === 401) return data.message || "Tu sesión terminó, vuelve a entrar"
  if (status === 403) return "No tienes permiso para hacer esto"
  if (status === 429) return data.message
  if (status >= 500) return "Algo salió mal, revisa los datos e intenta de nuevo"
  return data.message || "No se pudo completar la acción"
}

// Todo pasa por el api-gateway en /api
export async function api(path, { method = "GET", body } = {}) {
  const res = await fetch("/api" + path, {
    method,
    credentials: "same-origin",
    headers: body ? { "Content-Type": "application/json" } : {},
    body: body ? JSON.stringify(body) : undefined,
  })
  const data = await res.json().catch(() => ({}))
  if (res.status === 401 && path !== "/auth/login") onUnauthorized()
  if (!res.ok) throw new Error(errorMessage(res.status, data))
  return data.data
}

export function fullName(user) {
  if (!user) return "—"
  return [user.name, user.father_surname, user.mother_surname].filter(Boolean).join(" ")
}
