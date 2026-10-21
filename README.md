# Universidad Humanista - Intranet

Intranet escolar: grupos, estudiantes, maestros, materias, inscripciones, calificaciones, tareas y expedientes médicos.

## Servicios

| Servicio      | Puerto local | Que hace                                              |
| ------------- | ------------ | ----------------------------------------------------- |
| `api-gateway` | 8080         | Entrada unica, valida cada peticion con `auth`        |
| `user`        | 8081         | Usuarios (contraseñas con bcrypt)                     |
| `member`      | 8082         | Estudiantes, maestros y expedientes medicos           |
| `academic`    | 8083         | Grupos, materias, inscripciones, calificaciones, tareas |
| `auth`        | 8084         | Login, sesiones en redis, bloqueo por intentos        |
| `frontend`    | 5173         | React + shadcn                                        |

PostgreSQL guarda los datos y Redis las sesiones y el cache.

## Local

```bash
./scripts/dev.sh    # levanta postgres, redis, los servicios y el front
./scripts/stop.sh   # detiene todo
```

La primera vez se crea el `.env` con contraseñas aleatorias y se imprime la del admin.

## Kubernetes

```bash
./scripts/k8s-up.sh    # construye las imagenes y despliega en el namespace intranet
./scripts/k8s-down.sh
```

Funciona con k3s, minikube o Docker Desktop. El front queda en el puerto 80.

## Deploy automatico (VPS)

```bash
sudo ./webhook/install.sh
```

Instala el servicio `intranet-webhook` (puerto 9000) que corre `deploy.sh` en cada push a master. Al terminar imprime la URL y el secret para configurarlo en GitHub.
