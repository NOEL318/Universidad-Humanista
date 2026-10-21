import { fullName } from "@/lib/api"
import { useList } from "@/hooks/useList"
import CrudPage from "@/components/CrudPage"
import { PARTIALS, byId, label, studentOptions } from "@/lib/options"

export function ScoreBadge({ score }) {
  const color = score >= 8 ? "text-green-600" : score >= 6 ? "text-foreground" : "text-destructive"
  return <span className={`font-semibold tabular-nums ${color}`}>{score?.toFixed(1) ?? "—"}</span>
}

export default function Grades() {
  const { data: students } = useList("/student")
  const { data: subjects } = useList("/subject")
  const studentsByUser = byId(students, "user_id")
  const subjectsById = byId(subjects)

  const fields = [
    { name: "student_user_id", label: "Alumno", type: "select", options: studentOptions(students), full: true },
    { name: "subject_id", label: "Materia", type: "select", options: subjects.map((s) => ({ value: s.id, label: s.name })), full: true },
    { name: "partial", label: "Parcial", type: "select", options: PARTIALS },
    { name: "score", label: "Calificación (0 a 10)", type: "number", step: "0.1", min: 0, max: 10, required: true },
    { name: "notes", label: "Observaciones", type: "textarea", full: true },
  ]

  const columns = [
    { label: "Alumno", value: (r) => fullName(studentsByUser[r.student_user_id]) },
    { label: "Materia", value: (r) => subjectsById[r.subject_id]?.name },
    { label: "Parcial", value: (r) => label(PARTIALS, r.partial) },
    { label: "Calificación", value: (r) => r.score, render: (r) => <ScoreBadge score={r.score} /> },
  ]

  return <CrudPage title="Calificaciones" subtitle="Revisa y captura calificaciones." singular="Calificación" female path="/grade" columns={columns} fields={fields} />
}
