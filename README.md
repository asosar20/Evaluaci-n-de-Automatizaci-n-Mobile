# Mobile Testing Project

Proyecto de automatización de pruebas móviles usando **Serenity BDD** y **Appium** para aplicaciones Android e iOS.

## Requisitos Previos

Antes de comenzar, asegúrate de tener instalado lo siguiente:

- **Java JDK 17+**
- **Maven 3.9+**
- **Node.js y npm** (para Appium)
- **Appium Server 2.x**
- **Android Studio** (para testing Android)
- **Xcode** (para testing iOS, solo macOS)

## Instalación

### 1. Instalar Appium

```bash
npm install -g appium
appium driver install uiautomator2
appium driver install xcuitest
```

### 2. Verificar instalación de Appium

```bash
appium -v
appium driver list
```

### 3. Clonar el proyecto

```bash
git clone <repository-url>
```

### 4. Instalar dependencias del proyecto

```bash
mvn clean install -DskipTests
```

### 5. Probar solo en android
