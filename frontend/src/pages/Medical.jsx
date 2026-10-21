import { useState } from "react"
import { toast } from "sonner"
import { HeartPulse, Search } from "lucide-react"
import { api, fullName } from "@/lib/api"
import { useList } from "@/hooks/useList"
import { PageHeader, Panel } from "@/components/Layout"
import FormFields, { cleanForm } from "@/components/FormFields"
import { Badge } from "@/components/ui/badge"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Dialog, DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogTitle } from "@/components/ui/dialog"

const BLOOD = ["O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-"].map((b) => ({ value: b, label: b }))

export const MEDICAL_FIELDS = [
  { name: "blood_type", label: "Tipo de sangre", type: "select", options: BLOOD },
  { name: "allergies", label: "Alergias" },
  { name: "chronic_conditions", label: "Enfermedades crónicas", full: true },
  { name: "mental_health_conditions", label: "Salud mental", full: true },
  { name: "medications", label: "Medicamentos", full: true },
  { name: "disability", label: "Tiene alguna discapacidad", type: "checkbox" },
  { name: "disability_type", label: "Tipo de discapacidad" },
  { name: "mobility_aid", label: "Usa apoyo para moverse", type: "checkbox" },
  { name: "vision_problems", label: "Problemas de vista", type: "checkbox" },
  { name: "hearing_problems", label: "Problemas de audición", type: "checkbox" },
  { name: "has_health_insurance", label: "Tiene seguro médico", type: "checkbox" },
  { name: "insurance_provider", label: "Aseguradora" },
  { name: "insurance_policy_number", label: "Número de póliza" },
  { name: "insurance_valid_until", label: "Vigencia del seguro", type: "date" },
  { name: "emergency_contact_name", label: "Contacto de emergencia" },
  { name: "emergency_contact_relationship", label: "Parentesco" },
  { name: "emergency_contact_phone", label: "Teléfono de emergencia", type: "tel" },
  { name: "notes", label: "Notas", type: "textarea", full: true },
]

export default function Medical() {
  const { data: students } = useList("/student")
  const { data: teachers } = useList("/teacher")
  const [query, setQuery] = useState("")
  const [person, setPerson] = useState(null)
  const [form, setForm] = useState({})
  const [saving, setSaving] = useState(false)

  const people = [
    ...students.map((s) => ({ user_id: s.user_id, name: fullName(s), type: "Alumno", detail: s.enrollment_number })),
    ...teachers.map((t) => ({ user_id: t.userId, name: fullName(t.user), type: "Maestro", detail: t.employeeNumber })),
  ].filter((p) => `${p.name} ${p.detail}`.toLowerCase().includes(query.toLowerCase()))

  const open = async (p) => {
    setPerson(p)
    setForm({})
    try {
      setForm((await api(`/medical/user/${p.user_id}`)) ?? {})
    } catch (err) {
      toast.error(err.message)
    }
  }

  const submit = async (e) => {
    e.preventDefault()
    setSaving(true)
    try {
      await api(`/medical/user/${person.user_id}`, { method: "PUT", body: cleanForm(form, MEDICAL_FIELDS) })
      toast.success("Expediente guardado")
      setPerson(null)
    } catch (err) {
      toast.error(err.message)
    } finally {
      setSaving(false)
    }
  }

  return (
    <>
      <PageHeader title="Expedientes" subtitle="Información médica de alumnos y maestros." />
      <Panel className="p-0">
        <div className="flex items-center gap-2 border-b px-5 py-3">
          <Search className="size-4 text-muted-foreground" />
          <Input
            placeholder="Buscar por nombre o matrícula"
            value={query}
            onChange={(e) => setQuery(e.target.value)}
            className="h-8 border-0 bg-transparent shadow-none focus-visible:ring-0"
          />
        </div>
        <ul className="divide-y">
          {people.length === 0 && <li className="py-12 text-center text-muted-foreground">No hay personas registradas</li>}
          {people.map((p) => (
            <li key={p.user_id}>
              <button onClick={() => open(p)} className="flex w-full items-center gap-4 px-5 py-3 text-left transition-colors hover:bg-muted/60">
                <HeartPulse className="size-5 text-[#ff375f]" />
                <span className="flex-1">
                  <span className="block font-medium">{p.name}</span>
                  <span className="text-sm text-muted-foreground">{p.detail}</span>
                </span>
                <Badge variant="secondary">{p.type}</Badge>
              </button>
            </li>
          ))}
        </ul>
      </Panel>

      <Dialog open={Boolean(person)} onOpenChange={(v) => !v && setPerson(null)}>
        <DialogContent className="max-h-[90svh] overflow-y-auto rounded-3xl sm:max-w-2xl">
          <form onSubmit={submit} className="grid gap-6">
            <DialogHeader>
              <DialogTitle className="text-2xl tracking-tight">{person?.name}</DialogTitle>
              <DialogDescription>Expediente médico · {person?.type}</DialogDescription>
            </DialogHeader>
            <FormFields fields={MEDICAL_FIELDS} form={form} setForm={setForm} />
            <DialogFooter>
              <Button type="button" variant="ghost" className="rounded-full" onClick={() => setPerson(null)}>
                Cancelar
              </Button>
              <Button type="submit" className="rounded-full px-6" disabled={saving}>
                {saving ? "Guardando…" : "Guardar"}
              </Button>
            </DialogFooter>
          </form>
        </DialogContent>
      </Dialog>
    </>
  )
}
