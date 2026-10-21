import { useList } from "@/hooks/useList"
import CrudPage from "@/components/CrudPage"
import { byId } from "@/lib/options"

export const formatDate = (date) =>
  date ? new Date(date + "T00:00:00").toLocaleDateString("es-MX", { day: "numeric", month: "short", year: "numeric" }) : "—"

export default function Tasks() {
  const { data: subjects } = useList("/subject")
  const { data: groups } = useList("/group")
  const subjectsById = byId(subjects)
  const groupsById = byId(groups)

  const fields = [
    { name: "title", label: "Título", required: true, full: true },
    { name: "subject_id", label: "Materia", type: "select", options: subjects.map((s) => ({ value: s.id, label: s.name })) },
    { name: "group_id", label: "Grupo", type: "select", options: groups.map((g) => ({ value: g.id, label: g.name })) },
    { name: "due_date", label: "Fecha de entrega", type: "date" },
    { name: "description", label: "Instrucciones", type: "textarea", full: true },
  ]

  const columns = [
    { label: "Tarea", value: (r) => r.title },
    { label: "Materia", value: (r) => subjectsById[r.subject_id]?.name },
    { label: "Grupo", value: (r) => groupsById[r.group_id]?.name },
    { label: "Entrega", value: (r) => formatDate(r.due_date) },
  ]

  return <CrudPage title="Tareas" subtitle="Asigna tareas a tus grupos." singular="Tarea" female path="/task" columns={columns} fields={fields} />
}
