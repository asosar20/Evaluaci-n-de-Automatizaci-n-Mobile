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
cd mobile-testing-project
```

### 4. Instalar dependencias del proyecto

```bash
mvn clean install -DskipTests
```

## Configuración

### Estructura del Proyecto

```
demo_mobile/
├─ apps/               # Archivos .apk y .ipa de prueba
│ ├─ android/
│ └─ ios/
├─ src/
│ └─ test/
│ ├─ java/com/store/
│ │ ├─ exceptions/    # Manejo de excepciones personalizadas
│ │ ├─ hooks/         # Hooks para Appium/Serenity (inicio y cierre)
│ │ ├─ models/        # Modelos de datos usados en las pruebas
│ │ ├─ questions/     # Validaciones y consultas tipo Screenplay
│ │ ├─ runners/       # Runners de Serenity BDD (JUnit, Cucumber)
│ │ ├─ steps/         # Definiciones de pasos BDD (Step Definitions)
│ │ ├─ tasks/         # Acciones Screenplay (Login, Agregar al carrito)
│ │ ├─ ui/            # Mapeo de pantallas (Targets)
│ │ └─ utils/         # Utilidades generales (config, helpers)
│ └─ resources/
│ ├─ capabilities/    # Configuración Appium (Android / iOS)
│ ├─ environments/    # Configuración de entornos Serenity
│ ├─ features/        # Escenarios BDD (Cucumber)
│ ├─ log4j2.xml       # Configuración de logs
│ └─ serenity.conf    # Configuración global Serenity
├─ target/            # Reportes y compilación
├─ pom.xml            # Dependencias Maven
├─ build.gradle       # (opcional) Migración a Gradle
└─ README.md
```

### Archivo de Configuración Principal

El archivo `serenity.conf` contiene la configuración principal del proyecto:

```hocon
# =========================
# Serenity BDD - Config
# =========================
serenity {
  project.name = "mobile-testing-project"
  outputDirectory = "target/serenity"
  take.screenshots = AFTER_EACH_STEP
  logging = VERBOSE
}

# =========================
# WebDriver Configuration
# =========================
webdriver {
  driver = appium
  autodownload = false
  remote.url = "http://127.0.0.1:4723"
  timeouts {
    implicitlywait = 10000
    fluentwait = 10000
  }
}

# =========================
# Appium Hub Configuration
# =========================
appium {
  hub = "http://127.0.0.1:4723"
}

# =========================
# Environment por defecto
# =========================
environment = android

# =========================
# Device Type por defecto
# =========================
device.type = emulator

# =========================
# Capabilities Configuration
# =========================
capabilities {
  base.path = "/capabilities/"
  
  # Archivos por plataforma y tipo de dispositivo
  android.emulator.file = "android-emulator-capabilities.json"
  android.real.file = "android-real-capabilities.json"
  ios.simulator.file = "ios-simulator-capabilities.json"
  ios.real.file = "ios-real-capabilities.json"
}

# =========================
# Environments
# =========================
environments {
  android {
    webdriver.capabilities {
      "platformName" = "Android"
      "appium:automationName" = "UiAutomator2"
    }
  }
  
  ios {
    webdriver.capabilities {
      "platformName" = "iOS"
      "appium:automationName" = "XCUITest"
    }
  }
}
```

### Archivos de Capabilities

Crea los siguientes archivos JSON en `src/test/resources/capabilities/`:

#### Android Emulator

**Archivo:** `android-emulator-capabilities.json`

```json
{
  "appium:deviceName": "Pixel_6_API_33",
  "appium:platformVersion": "13.0",
  "appium:app": "/path/to/your/app.apk",
  "appium:autoGrantPermissions": true,
  "appium:noReset": false,
  "appium:fullReset": false,
  "appium:newCommandTimeout": 300
}
```

#### Android Real Device

**Archivo:** `android-real-capabilities.json`

```json
{
  "appium:deviceName": "Samsung Galaxy S21",
  "appium:udid": "your-device-udid",
  "appium:platformVersion": "12.0",
  "appium:app": "/path/to/your/app.apk",
  "appium:autoGrantPermissions": true,
  "appium:noReset": false,
  "appium:newCommandTimeout": 300
}
```

#### iOS Simulator

**Archivo:** `ios-simulator-capabilities.json`

```json
{
  "appium:deviceName": "iPhone 14 Pro",
  "appium:platformVersion": "16.0",
  "appium:app": "/path/to/your/app.app",
  "appium:autoAcceptAlerts": true,
  "appium:noReset": false,
  "appium:fullReset": false,
  "appium:newCommandTimeout": 300
}
```

#### iOS Real Device

**Archivo:** `ios-real-capabilities.json`

```json
{
  "appium:deviceName": "iPhone 13",
  "appium:udid": "your-device-udid",
  "appium:platformVersion": "15.0",
  "appium:app": "/path/to/your/app.ipa",
  "appium:xcodeOrgId": "your-team-id",
  "appium:xcodeSigningId": "iPhone Developer",
  "appium:autoAcceptAlerts": true,
  "appium:noReset": false,
  "appium:newCommandTimeout": 300
}
```

## Configuración de Dispositivos

### Configurar Android Emulator

1. Abre Android Studio
2. Ve a **Tools > Device Manager**
3. Crea un nuevo dispositivo virtual (AVD)
4. Anota el nombre del dispositivo y la versión de Android
5. Actualiza el archivo `android-emulator-capabilities.json` con estos valores

### Obtener UDID de dispositivo Android real

```bash
adb devices
```

### Obtener UDID de dispositivo iOS real

```bash
instruments -s devices
```

## Ejecución de Pruebas

### 1. Iniciar Appium Server

```bash
appium --base-path=/
```

O en una ventana separada:

```bash
appium
```

### 2. Ejecutar pruebas en Android Emulator (por defecto)

```bash
mvn clean verify
```

### 3. Ejecutar pruebas en Android Real Device

```bash
mvn clean verify -Ddevice.type=real
```

### 4. Ejecutar pruebas en iOS Simulator

```bash
mvn clean verify -Denvironment=ios -Ddevice.type=simulator
```

### 5. Ejecutar pruebas en iOS Real Device

```bash
mvn clean verify -Denvironment=ios -Ddevice.type=real
```

### 6. Ejecutar pruebas con tags específicos

```bash
mvn clean verify -Dcucumber.filter.tags="@smoke"
mvn clean test serenity:aggregate -Denvironment=android -Ddevice.type=emulator -Dcucumber.filter.tags="@youtube"
```

### 7. Ejecutar pruebas en paralelo

```bash
mvn clean verify -Dparallel.tests=2
```

## Reportes

Después de ejecutar las pruebas, los reportes de Serenity se generan automáticamente en:

```
target/serenity/index.html
```

Para abrir el reporte:

```bash
# Windows
start target/serenity/index.html

# macOS
open target/serenity/index.html

# Linux
xdg-open target/serenity/index.html
```

## Troubleshooting

### Appium no se conecta

- Verifica que Appium esté corriendo: `http://127.0.0.1:4723`
- Revisa los logs de Appium para más detalles

### El emulador no inicia

```bash
# Lista los AVDs disponibles
emulator -list-avds

# Inicia un AVD específico
emulator -avd Pixel_6_API_33
```

### Dispositivo real Android no detectado

```bash
# Verifica la conexión USB
adb devices

# Si no aparece, reinicia el servidor ADB
adb kill-server
adb start-server
```

### Problemas con iOS Real Device

- Asegúrate de tener un certificado de desarrollador válido
- Verifica que el dispositivo esté registrado en tu cuenta de desarrollador
- El UDID debe estar correctamente configurado

### Error de capabilities

- Verifica que los archivos JSON estén en la carpeta correcta: `src/test/resources/capabilities/`
- Asegúrate de que los archivos JSON tengan un formato válido
- Revisa que las rutas de las aplicaciones (`app`) sean correctas

## Comandos útiles

```bash
# Listar dispositivos Android conectados
adb devices

# Listar dispositivos iOS conectados
instruments -s devices

# Ver logs de Android en tiempo real
adb logcat

# Instalar APK manualmente
adb install path/to/app.apk

# Desinstalar APK
adb uninstall com.package.name

# Limpiar proyecto Maven
mvn clean

# Compilar sin ejecutar tests
mvn clean install -DskipTests

# Ver versión de Appium
appium -v

# Ver drivers instalados
appium driver list
```

## Contribución

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## Contacto

Adrian Matos - amatos3@gmail.com

---

Si este proyecto te fue útil, considera darle una estrella en GitHub
