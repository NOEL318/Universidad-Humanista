import { useState } from "react"
import { toast } from "sonner"
import { Pencil, Plus, Search, Trash2 } from "lucide-react"
import { api } from "@/lib/api"
import { useList } from "@/hooks/useList"
import { PageHeader, Panel } from "@/components/Layout"
import FormFields, { cleanForm } from "@/components/FormFields"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Dialog, DialogContent, DialogFooter, DialogHeader, DialogTitle } from "@/components/ui/dialog"
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table"

export default function CrudPage({ title, subtitle, singular, female, path, columns, fields, canEdit = true, save, remove, toForm }) {
  const { data: rows, loading, reload } = useList(path)
  const [open, setOpen] = useState(false)
  const [editing, setEditing] = useState(null)
  const [form, setForm] = useState({})
  const [query, setQuery] = useState("")
  const [saving, setSaving] = useState(false)
  const o = female ? "a" : "o"
  const name = singular.toLowerCase()

  const openForm = (row) => {
    setEditing(row)
    setForm(row ? (toForm ? toForm(row) : row) : {})
    setOpen(true)
  }

  const submit = async (e) => {
    e.preventDefault()
    setSaving(true)
    try {
      const values = cleanForm(form, fields)
      if (save) await save(values, editing)
      else if (editing) await api(`${path}/${editing.id}`, { method: "PUT", body: { ...editing, ...values } })
      else await api(path, { method: "POST", body: values })
      toast.success(editing ? "Cambios guardados" : `${singular} cread${o}`)
      setOpen(false)
      reload()
    } catch (err) {
      toast.error(err.message)
    } finally {
      setSaving(false)
    }
  }

  const destroy = async (row) => {
    if (!confirm(`¿Eliminar est${female ? "a" : "e"} ${name}? No se puede deshacer.`)) return
    try {
      if (remove) await remove(row)
      else await api(`${path}/${row.id}`, { method: "DELETE" })
      toast.success(`${singular} eliminad${o}`)
      reload()
    } catch (err) {
      toast.error(err.message)
    }
  }

  const filtered = rows.filter((row) =>
    columns.map((c) => c.value(row)).join(" ").toLowerCase().includes(query.toLowerCase())
  )

  return (
    <>
      <PageHeader title={title} subtitle={subtitle}>
        {canEdit && (
          <Button className="rounded-full px-5" onClick={() => openForm(null)}>
            <Plus /> Nuevo
          </Button>
        )}
      </PageHeader>

      <Panel className="p-0">
        <div className="flex items-center gap-2 border-b px-5 py-3">
          <Search className="size-4 text-muted-foreground" />
          <Input
            placeholder="Buscar"
            value={query}
            onChange={(e) => setQuery(e.target.value)}
            className="h-8 border-0 bg-transparent shadow-none focus-visible:ring-0"
          />
        </div>
        <div className="overflow-x-auto">
          <Table>
            <TableHeader>
              <TableRow>
                {columns.map((c) => (
                  <TableHead key={c.label} className="px-5 text-xs text-muted-foreground">
                    {c.label}
                  </TableHead>
                ))}
                {canEdit && <TableHead />}
              </TableRow>
            </TableHeader>
            <TableBody>
              {loading &&
                [1, 2, 3].map((i) => (
                  <TableRow key={i}>
                    <TableCell colSpan={columns.length + 1} className="px-5">
                      <div className="h-4 animate-pulse rounded bg-muted" />
                    </TableCell>
                  </TableRow>
                ))}
              {!loading && filtered.length === 0 && (
                <TableRow>
                  <TableCell colSpan={columns.length + 1} className="py-12 text-center text-muted-foreground">
                    {query ? "No hay resultados para tu búsqueda" : "Todavía no hay registros"}
                  </TableCell>
                </TableRow>
              )}
              {!loading &&
                filtered.map((row) => (
                  <TableRow key={row.id}>
                    {columns.map((c) => (
                      <TableCell key={c.label} className="px-5">
                        {c.render ? c.render(row) : c.value(row) ?? "—"}
                      </TableCell>
                    ))}
                    {canEdit && (
                      <TableCell className="px-5 text-right whitespace-nowrap">
                        <Button variant="ghost" size="icon-sm" onClick={() => openForm(row)} aria-label="Editar">
                          <Pencil />
                        </Button>
                        <Button variant="ghost" size="icon-sm" onClick={() => destroy(row)} aria-label="Eliminar">
                          <Trash2 className="text-destructive" />
                        </Button>
                      </TableCell>
                    )}
                  </TableRow>
                ))}
            </TableBody>
          </Table>
        </div>
      </Panel>

      <Dialog open={open} onOpenChange={setOpen}>
        <DialogContent className="max-h-[90svh] overflow-y-auto rounded-3xl sm:max-w-2xl">
          <form onSubmit={submit} className="grid gap-6">
            <DialogHeader>
              <DialogTitle className="text-2xl tracking-tight">{editing ? `Editar ${name}` : `Nuev${o} ${name}`}</DialogTitle>
            </DialogHeader>
            <FormFields fields={fields} form={form} setForm={setForm} editing={Boolean(editing)} />
            <DialogFooter>
              <Button type="button" variant="ghost" className="rounded-full" onClick={() => setOpen(false)}>
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
