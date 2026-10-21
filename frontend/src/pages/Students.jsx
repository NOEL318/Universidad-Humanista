import { api, fullName } from "@/lib/api"
import { useAuth } from "@/auth"
import { useList } from "@/hooks/useList"
import CrudPage from "@/components/CrudPage"
import { Badge } from "@/components/ui/badge"
import { STUDENT_STATUS, TURNS, USER_FIELDS, USER_KEYS, byId, label, pick } from "@/lib/options"

const STUDENT_KEYS = ["enrollment_number", "academic_program", "semester", "status", "group_id", "turn"]

// Primero se crea el usuario y luego el alumno con su user_id
async function save(values, editing) {
  const user = { ...pick(values, USER_KEYS), role: "student" }
  const student = pick(values, STUDENT_KEYS)
  if (editing) {
    await api("/user", { method: "PUT", body: { ...editing, ...user, id: editing.user_id } })
    await api(`/student/${editing.id}`, { method: "PUT", body: { ...editing, ...student } })
    return
  }
  const created = await api("/user", { method: "POST", body: user })
  try {
    await api("/student", { method: "POST", body: { ...student, user_id: created.id } })
  } catch (err) {
    await api(`/user/${created.id}`, { method: "DELETE" })
    throw err
  }
}

async function remove(row) {
  await api(`/student/${row.id}`, { method: "DELETE" })
  await api(`/user/${row.user_id}`, { method: "DELETE" })
}

export default function Students() {
  const { user } = useAuth()
  const { data: groups } = useList("/group")
  const groupsById = byId(groups)

  const fields = [
    ...USER_FIELDS,
    { name: "enrollment_number", label: "Matrícula", required: true },
    { name: "academic_program", label: "Programa académico", required: true },
    { name: "semester", label: "Semestre", required: true },
    { name: "status", label: "Estatus", type: "select", options: STUDENT_STATUS },
    { name: "group_id", label: "Grupo", type: "select", options: groups.map((g) => ({ value: g.id, label: g.name })) },
    { name: "turn", label: "Turno", type: "select", options: TURNS },
  ]

  const columns = [
    { label: "Matrícula", value: (r) => r.enrollment_number },
    { label: "Nombre", value: (r) => fullName(r) },
    { label: "Programa", value: (r) => r.academic_program },
    { label: "Semestre", value: (r) => r.semester },
    { label: "Grupo", value: (r) => groupsById[r.group_id]?.name },
    { label: "Estatus", value: (r) => label(STUDENT_STATUS, r.status), render: (r) => <Badge variant="secondary">{label(STUDENT_STATUS, r.status)}</Badge> },
  ]

  return (
    <CrudPage
      title="Estudiantes"
      subtitle="Alumnos inscritos en la universidad."
      singular="Estudiante"
      path="/student"
      columns={columns}
      fields={fields}
      canEdit={user.role === "admin"}
      save={save}
      remove={remove}
      toForm={(row) => ({ ...row, password: "" })}
    />
  )
}
