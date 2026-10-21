import { useCallback, useEffect, useState } from "react"
import { toast } from "sonner"
import { api } from "@/lib/api"

export function useList(path) {
  const [data, setData] = useState([])
  const [loading, setLoading] = useState(Boolean(path))

  const reload = useCallback(() => {
    if (!path) return
    setLoading(true)
    return api(path)
      .then((d) => setData(d ?? []))
      .catch((e) => toast.error(e.message))
      .finally(() => setLoading(false))
  }, [path])

  useEffect(() => {
    reload()
  }, [reload])

  return { data, loading, reload }
}
