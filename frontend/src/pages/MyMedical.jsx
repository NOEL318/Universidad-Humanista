import { useEffect, useState } from "react"
import { api } from "@/lib/api"
import { PageHeader, Panel } from "@/components/Layout"
import FormFields from "@/components/FormFields"
import { MEDICAL_FIELDS } from "@/pages/Medical"

export default function MyMedical() {
  const [record, setRecord] = useState(undefined)

  useEffect(() => {
    api("/medical/me").then(setRecord).catch(() => setRecord(null))
  }, [])

  return (
    <>
      <PageHeader title="Mi expediente" subtitle="Si algo no es correcto, acude a servicios escolares." />
      <Panel>
        {record === undefined && <div className="h-40 animate-pulse rounded-2xl bg-muted" />}
        {record === null && <p className="py-8 text-center text-muted-foreground">Aún no tienes expediente médico registrado.</p>}
        {record && <FormFields fields={MEDICAL_FIELDS} form={record} setForm={() => {}} disabled />}
      </Panel>
    </>
  )
}
