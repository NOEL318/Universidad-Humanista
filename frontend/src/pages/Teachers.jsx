import { api, fullName } from "@/lib/api"
import CrudPage from "@/components/CrudPage"
import { Badge } from "@/components/ui/badge"
import { TEACHER_STATUS, TURNS, USER_FIELDS, USER_KEYS, label, pick } from "@/lib/options"

const TEACHER_KEYS = ["employeeNumber", "department", "specialization", "contractType", "status", "turn"]

const fields = [
  ...USER_FIELDS,
  { name: "employeeNumber", label: "Número de empleado", required: true },
  { name: "department", label: "Departamento" },
  { name: "specialization", label: "Especialidad" },
  { name: "contractType", label: "Tipo de contrato" },
  { name: "status", label: "Estatus", type: "select", options: TEACHER_STATUS },
  { name: "turn", label: "Turno", type: "select", options: TURNS },
]

const columns = [
  { label: "Empleado", value: (r) => r.employeeNumber },
  { label: "Nombre", value: (r) => fullName(r.user) },
  { label: "Departamento", value: (r) => r.department },
  { label: "Especialidad", value: (r) => r.specialization },
  { label: "Estatus", value: (r) => label(TEACHER_STATUS, r.status), render: (r) => <Badge variant="secondary">{label(TEACHER_STATUS, r.status)}</Badge> },
]

async function save(values, editing) {
  const user = { ...pick(values, USER_KEYS), role: "teacher" }
  const teacher = pick(values, TEACHER_KEYS)
  if (editing) {
    await api("/user", { method: "PUT", body: { ...editing.user, ...user, id: editing.userId } })
    await api(`/teacher/${editing.id}`, { method: "PUT", body: { ...editing, ...teacher, user: null } })
    return
  }
  const created = await api("/user", { method: "POST", body: user })
  try {
    await api("/teacher", { method: "POST", body: { ...teacher, userId: created.id } })
  } catch (err) {
    await api(`/user/${created.id}`, { method: "DELETE" })
    throw err
  }
}

async function remove(row) {
  await api(`/teacher/${row.id}`, { method: "DELETE" })
  await api(`/user/${row.userId}`, { method: "DELETE" })
}

export default function Teachers() {
  return (
    <CrudPage
      title="Maestros"
      subtitle="Personal docente."
      singular="Maestro"
      path="/teacher"
      columns={columns}
      fields={fields}
      save={save}
      remove={remove}
      toForm={(row) => ({ ...row, ...row.user, id: row.id, status: row.status, password: "" })}
    />
  )
}
