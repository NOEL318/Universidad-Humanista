import { fullName } from "@/lib/api"
import { useList } from "@/hooks/useList"
import CrudPage from "@/components/CrudPage"
import { byId, teacherOptions } from "@/lib/options"

export default function Subjects() {
  const { data: teachers } = useList("/teacher")
  const { data: groups } = useList("/group")
  const teachersByUser = byId(teachers, "userId")
  const groupsById = byId(groups)

  const fields = [
    { name: "name", label: "Nombre", required: true },
    { name: "code", label: "Clave" },
    { name: "credits", label: "Créditos", type: "number", min: 0 },
    { name: "group_id", label: "Grupo", type: "select", options: groups.map((g) => ({ value: g.id, label: g.name })) },
    { name: "teacher_user_id", label: "Maestro", type: "select", options: teacherOptions(teachers), full: true },
    { name: "description", label: "Descripción", type: "textarea", full: true },
  ]

  const columns = [
    { label: "Clave", value: (r) => r.code },
    { label: "Materia", value: (r) => r.name },
    { label: "Créditos", value: (r) => r.credits },
    { label: "Grupo", value: (r) => groupsById[r.group_id]?.name },
    { label: "Maestro", value: (r) => fullName(teachersByUser[r.teacher_user_id]?.user) },
  ]

  return <CrudPage title="Materias" subtitle="Plan de estudios." singular="Materia" female path="/subject" columns={columns} fields={fields} />
}
