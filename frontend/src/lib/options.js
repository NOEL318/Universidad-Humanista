import { fullName } from "@/lib/api"

const list = (...items) => items.map(([value, label]) => ({ value, label }))

export const TURNS = list(["matutino", "Matutino"], ["vespertino", "Vespertino"], ["mixto", "Mixto"])
export const SEX = list(["M", "Masculino"], ["F", "Femenino"], ["O", "Otro"])
export const STUDENT_STATUS = list(["active", "Activo"], ["suspended", "Suspendido"], ["graduated", "Egresado"], ["off", "Baja"])
export const TEACHER_STATUS = list(["active", "Activo"], ["license", "Licencia"], ["jubilated", "Jubilado"], ["off", "Baja"])
export const ENROLLMENT_STATUS = list(["active", "Cursando"], ["finished", "Terminada"], ["dropped", "Dada de baja"])
export const PARTIALS = list(["1", "Parcial 1"], ["2", "Parcial 2"], ["3", "Parcial 3"], ["final", "Final"])

export const label = (options, value) => options.find((o) => o.value === value)?.label ?? value ?? "—"

export const studentOptions = (students) => students.map((s) => ({ value: s.user_id, label: `${fullName(s)} · ${s.enrollment_number}` }))
export const teacherOptions = (teachers) => teachers.map((t) => ({ value: t.userId, label: fullName(t.user) }))
export const byId = (items, key = "id") => Object.fromEntries(items.map((i) => [i[key], i]))

export const USER_FIELDS = [
  { name: "name", label: "Nombre", required: true },
  { name: "father_surname", label: "Apellido paterno", required: true },
  { name: "mother_surname", label: "Apellido materno", required: true },
  { name: "born_date", label: "Fecha de nacimiento", type: "date", required: true },
  { name: "sex", label: "Sexo", type: "select", options: SEX },
  { name: "curp", label: "CURP", required: true },
  { name: "phone", label: "Teléfono", type: "tel", required: true },
  { name: "address", label: "Dirección", required: true },
  { name: "email", label: "Correo", type: "email", required: true },
  { name: "password", label: "Contraseña", type: "password", required: true, optionalOnEdit: true },
]

export const USER_KEYS = USER_FIELDS.map((f) => f.name)

export const pick = (values, keys) => Object.fromEntries(keys.map((k) => [k, values[k]]))
