import { fullName } from "@/lib/api"
import { useList } from "@/hooks/useList"
import CrudPage from "@/components/CrudPage"
import { TURNS, byId, label, teacherOptions } from "@/lib/options"

export default function Groups() {
  const { data: teachers } = useList("/teacher")
  const teachersByUser = byId(teachers, "userId")

  const fields = [
    { name: "name", label: "Nombre", required: true },
    { name: "semester", label: "Semestre" },
    { name: "turn", label: "Turno", type: "select", options: TURNS },
    { name: "school_year", label: "Ciclo escolar" },
    { name: "advisor_user_id", label: "Tutor", type: "select", options: teacherOptions(teachers), full: true },
  ]

  const columns = [
    { label: "Grupo", value: (r) => r.name },
    { label: "Semestre", value: (r) => r.semester },
    { label: "Turno", value: (r) => label(TURNS, r.turn) },
    { label: "Ciclo", value: (r) => r.school_year },
    { label: "Tutor", value: (r) => fullName(teachersByUser[r.advisor_user_id]?.user) },
  ]

  return <CrudPage title="Grupos" subtitle="Grupos por semestre y turno." singular="Grupo" path="/group" columns={columns} fields={fields} />
}
