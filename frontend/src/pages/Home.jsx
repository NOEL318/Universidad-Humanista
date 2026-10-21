import { useEffect, useState } from "react"
import { Link } from "react-router-dom"
import { BookOpen, ClipboardList, GraduationCap, Users } from "lucide-react"
import { api } from "@/lib/api"
import { useAuth } from "@/auth"
import { useList } from "@/hooks/useList"
import { PageHeader, Panel } from "@/components/Layout"
import { ScoreBadge } from "@/pages/Grades"
import { formatDate } from "@/pages/Tasks"
import { PARTIALS, byId, label } from "@/lib/options"

const firstName = (name) => name?.split(" ")[0]

function Stat({ to, icon: Icon, label, value, color }) {
  return (
    <Link to={to}>
      <Panel className="transition-transform hover:scale-[1.02]">
        <Icon className="size-6" style={{ color }} />
        <p className="mt-6 text-4xl font-semibold tracking-tight tabular-nums">{value}</p>
        <p className="mt-1 text-sm text-muted-foreground">{label}</p>
      </Panel>
    </Link>
  )
}

function TaskList({ tasks, subjectsById }) {
  if (tasks.length === 0) return <p className="py-6 text-center text-muted-foreground">No hay tareas pendientes.</p>
  return (
    <ul className="divide-y">
      {tasks.map((t) => (
        <li key={t.id} className="flex items-start justify-between gap-4 py-3">
          <div>
            <p className="font-medium">{t.title}</p>
            <p className="text-sm text-muted-foreground">{subjectsById[t.subject_id]?.name}</p>
          </div>
          <span className="shrink-0 text-sm text-muted-foreground">{formatDate(t.due_date)}</span>
        </li>
      ))}
    </ul>
  )
}

function StaffHome() {
  const students = useList("/student").data
  const groups = useList("/group").data
  const subjects = useList("/subject").data
  const tasks = useList("/task").data
  const today = new Date().toISOString().slice(0, 10)
  const upcoming = tasks.filter((t) => !t.due_date || t.due_date >= today).sort((a, b) => (a.due_date ?? "").localeCompare(b.due_date ?? "")).slice(0, 6)

  return (
    <div className="grid gap-6">
      <div className="grid grid-cols-2 gap-4 md:grid-cols-4">
        <Stat to="/estudiantes" icon={GraduationCap} label="Estudiantes" value={students.length} color="#0071e3" />
        <Stat to="/grupos" icon={Users} label="Grupos" value={groups.length} color="#34c759" />
        <Stat to="/materias" icon={BookOpen} label="Materias" value={subjects.length} color="#ff9f0a" />
        <Stat to="/tareas" icon={ClipboardList} label="Tareas" value={tasks.length} color="#af52de" />
      </div>
      <Panel>
        <h2 className="text-xl font-semibold tracking-tight">Próximas entregas</h2>
        <TaskList tasks={upcoming} subjectsById={byId(subjects)} />
      </Panel>
    </div>
  )
}

function StudentHome() {
  const [me, setMe] = useState(null)
  const grades = useList("/grade/me").data
  const subjects = useList("/subject").data
  const { data: tasks } = useList(me?.group_id ? `/task/group/${me.group_id}` : null)
  const subjectsById = byId(subjects)

  useEffect(() => {
    api("/student/me").then(setMe).catch(() => {})
  }, [])

  return (
    <div className="grid gap-6 md:grid-cols-3">
      <Panel>
        <p className="text-sm text-muted-foreground">Matrícula</p>
        <p className="text-2xl font-semibold tracking-tight">{me?.enrollment_number ?? "—"}</p>
        <p className="mt-4 text-sm text-muted-foreground">Programa</p>
        <p className="font-medium">{me?.academic_program ?? "—"}</p>
        <p className="mt-4 text-sm text-muted-foreground">Semestre</p>
        <p className="font-medium">{me?.semester ?? "—"}</p>
      </Panel>
      <Panel className="md:col-span-2">
        <h2 className="text-xl font-semibold tracking-tight">Mis calificaciones</h2>
        {grades.length === 0 ? (
          <p className="py-6 text-center text-muted-foreground">Todavía no tienes calificaciones.</p>
        ) : (
          <ul className="divide-y">
            {grades.map((g) => (
              <li key={g.id} className="flex items-center justify-between py-3">
                <span>
                  <span className="block font-medium">{subjectsById[g.subject_id]?.name}</span>
                  <span className="text-sm text-muted-foreground">{label(PARTIALS, g.partial)}</span>
                </span>
                <ScoreBadge score={g.score} />
              </li>
            ))}
          </ul>
        )}
      </Panel>
      <Panel className="md:col-span-3">
        <h2 className="text-xl font-semibold tracking-tight">Mis tareas</h2>
        <TaskList tasks={tasks} subjectsById={subjectsById} />
      </Panel>
    </div>
  )
}

export default function Home() {
  const { user } = useAuth()
  return (
    <>
      <PageHeader title={`Hola, ${firstName(user.name)}.`} subtitle="Esto es lo que pasa hoy en la universidad." />
      {user.role === "student" ? <StudentHome /> : <StaffHome />}
    </>
  )
}
