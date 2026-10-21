import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Textarea } from "@/components/ui/textarea"
import { Checkbox } from "@/components/ui/checkbox"
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"
import { cn } from "@/lib/utils"

export default function FormFields({ fields, form, setForm, editing, disabled }) {
  const set = (name, value) => setForm((f) => ({ ...f, [name]: value }))

  return (
    <div className="grid gap-4 sm:grid-cols-2">
      {fields.map((field) => {
        const value = form[field.name] ?? ""
        const required = field.required && !(editing && field.optionalOnEdit)
        return (
          <div key={field.name} className={cn("grid gap-1.5", field.full && "sm:col-span-2")}>
            {field.type !== "checkbox" && (
              <Label htmlFor={field.name} className="text-xs text-muted-foreground">
                {field.label}
                {editing && field.optionalOnEdit && " (déjalo vacío para no cambiarla)"}
              </Label>
            )}
            {field.type === "select" ? (
              <Select value={value ? String(value) : ""} onValueChange={(v) => set(field.name, v)} disabled={disabled}>
                <SelectTrigger id={field.name} className="w-full">
                  <SelectValue placeholder="Selecciona…" />
                </SelectTrigger>
                <SelectContent>
                  {field.options.map((o) => (
                    <SelectItem key={o.value} value={String(o.value)}>
                      {o.label}
                    </SelectItem>
                  ))}
                </SelectContent>
              </Select>
            ) : field.type === "textarea" ? (
              <Textarea id={field.name} value={value} disabled={disabled} onChange={(e) => set(field.name, e.target.value)} />
            ) : field.type === "checkbox" ? (
              <label className="flex items-center gap-2 pt-5 text-sm">
                <Checkbox checked={Boolean(value)} disabled={disabled} onCheckedChange={(v) => set(field.name, v === true)} />
                {field.label}
              </label>
            ) : (
              <Input
                id={field.name}
                type={field.type || "text"}
                value={value}
                required={required}
                disabled={disabled}
                step={field.step}
                min={field.min}
                max={field.max}
                autoComplete={field.type === "password" ? "new-password" : "off"}
                onChange={(e) => set(field.name, e.target.value)}
              />
            )}
          </div>
        )
      })}
    </div>
  )
}

// Convierte numeros y quita vacios antes de mandar
export function cleanForm(form, fields) {
  const values = { ...form }
  fields.forEach((f) => {
    if (values[f.name] === "") values[f.name] = null
    if (f.type === "number" && values[f.name] != null) values[f.name] = Number(values[f.name])
  })
  return values
}
