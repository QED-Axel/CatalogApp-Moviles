# Etapa 1: Build
FROM node:22-alpine AS builder

WORKDIR /app

# Copiar configuración de dependencias
COPY package*.json ./
COPY tsconfig.build.json ./
COPY tsconfig.json ./
COPY prisma ./prisma/

# Instalar dependencias (incluyendo devDependencies para compilar)
RUN npm install

# Generar el cliente de Prisma
RUN npx prisma generate

# Copiar el código fuente y compilar
COPY src ./src/
RUN npm run build

# Etapa 2: Producción
FROM node:22-alpine AS production

WORKDIR /app

COPY package*.json ./
# Instalar solo dependencias de producción
RUN npm install --only=production
# Instalar prisma CLI temporalmente para las migraciones
RUN npm install prisma --no-save

# Copiar el cliente generado de Prisma (incluyendo el engine) y los binarios compilados
COPY --from=builder /app/node_modules/.prisma ./node_modules/.prisma
COPY --from=builder /app/node_modules/@prisma/client ./node_modules/@prisma/client
COPY --from=builder /app/dist ./dist
COPY --from=builder /app/prisma ./prisma

# Puerto que expone el contenedor
EXPOSE 3000

# Script de inicio: Corre las migraciones y luego levanta la API
CMD ["sh", "-c", "npx prisma migrate deploy && node dist/main"]
