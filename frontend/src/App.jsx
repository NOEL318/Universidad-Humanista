import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom"
import { AuthProvider, useAuth } from "@/auth"
import { Toaster } from "@/components/ui/sonner"
import Layout from "@/components/Layout"
import Login from "@/pages/Login"
import Home from "@/pages/Home"
import Students from "@/pages/Students"
import Teachers from "@/pages/Teachers"
import Groups from "@/pages/Groups"
import Subjects from "@/pages/Subjects"
import Enrollments from "@/pages/Enrollments"
import Grades from "@/pages/Grades"
import Tasks from "@/pages/Tasks"
import Medical from "@/pages/Medical"
import MyMedical from "@/pages/MyMedical"

function Private({ roles, children }) {
  const { user, loading } = useAuth()
  if (loading) return null
  if (!user) return <Navigate to="/login" replace />
  if (roles && !roles.includes(user.role)) return <Navigate to="/" replace />
  return children
}

const STAFF = ["admin", "teacher"]

export default function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route element={<Private><Layout /></Private>}>
            <Route index element={<Home />} />
            <Route path="estudiantes" element={<Private roles={STAFF}><Students /></Private>} />
            <Route path="maestros" element={<Private roles={["admin"]}><Teachers /></Private>} />
            <Route path="grupos" element={<Private roles={STAFF}><Groups /></Private>} />
            <Route path="materias" element={<Private roles={STAFF}><Subjects /></Private>} />
            <Route path="inscripciones" element={<Private roles={STAFF}><Enrollments /></Private>} />
            <Route path="calificaciones" element={<Private roles={STAFF}><Grades /></Private>} />
            <Route path="tareas" element={<Private roles={STAFF}><Tasks /></Private>} />
            <Route path="expedientes" element={<Private roles={STAFF}><Medical /></Private>} />
            <Route path="mi-expediente" element={<Private roles={["student"]}><MyMedical /></Private>} />
          </Route>
          <Route path="*" element={<Navigate to="/" replace />} />
        </Routes>
      </BrowserRouter>
      <Toaster position="top-center" richColors />
    </AuthProvider>
  )
}
