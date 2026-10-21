import { fullName } from "@/lib/api"
import { useList } from "@/hooks/useList"
import CrudPage from "@/components/CrudPage"
import { ENROLLMENT_STATUS, byId, label, studentOptions } from "@/lib/options"

export default function Enrollments() {
  const { data: students } = useList("/student")
  const { data: subjects } = useList("/subject")
  const studentsByUser = byId(students, "user_id")
  const subjectsById = byId(subjects)

  const fields = [
    { name: "student_user_id", label: "Alumno", type: "select", options: studentOptions(students), full: true },
    { name: "subject_id", label: "Materia", type: "select", options: subjects.map((s) => ({ value: s.id, label: s.name })), full: true },
    { name: "period", label: "Periodo", required: true },
    { name: "status", label: "Estatus", type: "select", options: ENROLLMENT_STATUS },
  ]

  const columns = [
    { label: "Alumno", value: (r) => fullName(studentsByUser[r.student_user_id]) },
    { label: "Materia", value: (r) => subjectsById[r.subject_id]?.name },
    { label: "Periodo", value: (r) => r.period },
    { label: "Estatus", value: (r) => label(ENROLLMENT_STATUS, r.status) },
  ]

  return (
    <CrudPage
      title="Inscripciones"
      subtitle="Alumnos inscritos a cada materia."
      singular="Inscripción" female
      path="/enrollment"
      columns={columns}
      fields={fields}
    />
  )
}
