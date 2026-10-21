import { NavLink, Outlet } from "react-router-dom"
import { LogOut } from "lucide-react"
import { useAuth } from "@/auth"
import { Button } from "@/components/ui/button"
import { cn } from "@/lib/utils"

const LINKS = {
  admin: [
    ["/", "Inicio"],
    ["/estudiantes", "Estudiantes"],
    ["/maestros", "Maestros"],
    ["/grupos", "Grupos"],
    ["/materias", "Materias"],
    ["/inscripciones", "Inscripciones"],
    ["/calificaciones", "Calificaciones"],
    ["/tareas", "Tareas"],
    ["/expedientes", "Expedientes"],
  ],
  teacher: [
    ["/", "Inicio"],
    ["/estudiantes", "Estudiantes"],
    ["/grupos", "Grupos"],
    ["/materias", "Materias"],
    ["/inscripciones", "Inscripciones"],
    ["/calificaciones", "Calificaciones"],
    ["/tareas", "Tareas"],
    ["/expedientes", "Expedientes"],
  ],
  student: [
    ["/", "Inicio"],
    ["/mi-expediente", "Mi expediente"],
  ],
}

export default function Layout() {
  const { user, logout } = useAuth()

  return (
    <div className="min-h-svh">
      <header className="sticky top-0 z-40 border-b border-black/5 bg-white/75 backdrop-blur-xl">
        <div className="mx-auto flex max-w-6xl flex-wrap items-center justify-between gap-x-6 px-4 pt-2 md:h-12 md:flex-nowrap md:pt-0">
          <span className="shrink-0 text-sm font-semibold tracking-tight">Universidad Humanista</span>
          <nav className="order-last -mx-4 flex w-full gap-5 overflow-x-auto px-4 py-2 text-xs whitespace-nowrap md:order-none md:mx-0 md:w-auto md:flex-1 md:px-0">
            {LINKS[user.role]?.map(([to, label]) => (
              <NavLink
                key={to}
                to={to}
                end
                className={({ isActive }) =>
                  cn("py-1 text-foreground/70 transition-colors hover:text-foreground", isActive && "text-foreground")
                }
              >
                {label}
              </NavLink>
            ))}
          </nav>
          <Button variant="ghost" size="sm" className="shrink-0 rounded-full text-xs" onClick={logout}>
            <LogOut /> Salir
          </Button>
        </div>
      </header>
      <main className="mx-auto max-w-6xl px-4 py-10 md:py-14">
        <Outlet />
      </main>
    </div>
  )
}

export function PageHeader({ title, subtitle, children }) {
  return (
    <div className="mb-8 flex flex-wrap items-end justify-between gap-4">
      <div>
        <h1 className="text-4xl font-semibold tracking-tight md:text-5xl">{title}</h1>
        {subtitle && <p className="mt-2 text-lg text-muted-foreground">{subtitle}</p>}
      </div>
      {children}
    </div>
  )
}

export function Panel({ className, children }) {
  return <section className={cn("rounded-3xl bg-card p-6 shadow-[0_2px_12px_rgba(0,0,0,0.04)]", className)}>{children}</section>
}
