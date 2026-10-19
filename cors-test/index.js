
// URL base de la API de Spring Boot.
const API_URL = "http://localhost:4001";


 // Ejemplo de implementación de una petición POST:
async function postData(url = "", data = {}) {
  // Las opciones marcadas con * son las opciones predeterminadas.
  const response = await fetch(url, {
    method: "POST", // Método HTTP utilizado para enviar datos al servidor.
    mode: "cors", // *cors: permite solicitudes entre distintos orígenes 
    cache: "no-cache", // *default: comportamiento de caché predeterminado; 
    credentials: "same-origin", // include: envía credenciales también a otros 
    headers: {
      "Content-Type": "application/json", // Indica al servidor el formato del contenido que se envía en el cuerpo (body)
    },
    redirect: "follow", // manual: gestiona manualmente las redirecciones;
    referrerPolicy: "no-referrer", // Controla qué información del origen de la petición se envía en la cabecera Referer.
    body: JSON.stringify(data), // Convierte el objeto JavaScript data en una cadena JSON para enviarla en el cuerpo de la petición. Debe coincidir con el formato indicado en Content-Type.
  });
  return response.json(); // Lee el cuerpo de la respuesta e interpreta el JSON para convertirlo en un objeto JavaScript.
}

async function getData(url = "", jwt = null) {
  const response = await fetch(url, {
    method: "GET",
    mode: "cors", 
    cache: "no-cache",
    credentials: "same-origin",
    headers: {
      "Content-Type": "application/json",
      Authorization: "Bearer ".concat(jwt), // Envía el JWT en la cabecera Authorization usando el esquema Bearer
    },
    redirect: "follow", 
    referrerPolicy: "no-referrer", 
  });
  return response.json();
}

// Referencias a los elementos de la interfaz.
const loginForm = document.getElementById("loginForm");
const registerForm = document.getElementById("registerForm");
const serverResponse = document.getElementById("serverResponse");
const jwtInput = document.getElementById("jwt");
const statusMessage = document.getElementById("statusMessage");

// Muestra la respuesta JSON del servidor en el panel superior.
function showResponse(data) {
    serverResponse.textContent = JSON.stringify(data, null, 2);
}

// Actualiza el JWT únicamente cuando la respuesta incluye ese campo.
function updateJwt(data) {
    if (data && typeof data.jwt === "string") {
        jwtInput.value = data.jwt;
    }
}

// Muestra mensajes de estado sin modificar el JSON recibido.
function showStatus(message, isError = false) {
    statusMessage.textContent = message;
    statusMessage.className = isError
        ? "status-message error"
        : "status-message success";
}

// Presenta los errores HTTP y de conexión en la interfaz.
function handleError(error) {
    console.error(error);

    if (error.payload !== undefined) {
        showResponse(error.payload);
    } else {
        showResponse({
            error: error.message || "No se pudo conectar con el servidor.",
        });
    }

    showStatus(
        error.status
            ? `La petición falló con HTTP ${error.status}.`
            : "No se pudo completar la petición. Revisa la conexión y la consola.",
        true
    );
}

// LOGIN
// Envía el usuario y la contraseña introducidos en el formulario.
loginForm.addEventListener("submit", async (event) => {
    event.preventDefault();

    const submitButton = document.getElementById("login");
    submitButton.disabled = true;

    const data = {
        username: document.getElementById("loginUsername").value.trim(),
        password: document.getElementById("loginPassword").value,
    };

    try {
        const result = await postData(
            `${API_URL}/api/v1/auth/authenticate`,
            data
        );

        showResponse(result);
        updateJwt(result);
        showStatus("Inicio de sesión completado correctamente.");
    } catch (error) {
        handleError(error);
    } finally {
        submitButton.disabled = false;
    }
});

// REGISTRO
// Envía los cuatro campos requeridos por el endpoint de clientes.
registerForm.addEventListener("submit", async (event) => {
    event.preventDefault();

    const submitButton = document.getElementById("register");
    submitButton.disabled = true;

    const password = document.getElementById("registerPassword").value;
    const repeatedPassword =
        document.getElementById("repeatedPassword").value;

    if (password !== repeatedPassword) {
        showStatus("Las contraseñas no coinciden.", true);
        submitButton.disabled = false;
        return;
    }

    const data = {
        name: document.getElementById("registerName").value.trim(),
        username: document.getElementById("registerUsername").value.trim(),
        password: password,
        repeatedPassword: repeatedPassword,
    };

    try {
        const result = await postData(
            `${API_URL}/api/v1/customers`,
            data
        );

        showResponse(result);
        updateJwt(result);
        showStatus("Usuario registrado correctamente.");
        registerForm.reset();
    } catch (error) {
        handleError(error);
    } finally {
        submitButton.disabled = false;
    }
});

// Copia el JSON mostrado en el panel de respuesta.
document.getElementById("copyResponse").addEventListener("click", async () => {
    try {
        await navigator.clipboard.writeText(serverResponse.textContent);
        showStatus("Respuesta del servidor copiada.");
    } catch (error) {
        showStatus("No se pudo copiar la respuesta.", true);
    }
});

// Copia el JWT sin incluir otros datos de la respuesta.
document.getElementById("copyJwt").addEventListener("click", async () => {
    if (!jwtInput.value) {
        showStatus("Todavía no hay ningún JWT para copiar.", true);
        return;
    }

    try {
        await navigator.clipboard.writeText(jwtInput.value);
        showStatus("JWT copiado correctamente.");
    } catch (error) {
        showStatus("No se pudo copiar el JWT.", true);
    }
});


// CONSULTAR PRODUCTOS
// Obtiene una página de productos utilizando el JWT.
document.getElementById("findProducts").addEventListener("click", async () => {
    const jwt = jwtInput.value.trim();
    const button = document.getElementById("findProducts");

    if (!jwt) {
        showStatus("Inicia sesión o registra un usuario para obtener el JWT.", true);
        return;
    }

    button.disabled = true;

    try {
        const result = await getData(
            `${API_URL}/api/v1/products?p=0&limit=5`,
            jwt
        );

        showResponse(result);
        showStatus("Productos obtenidos correctamente.");
    } catch (error) {
        handleError(error);
    } finally {
        button.disabled = false;
    }
});


// CONSULTAR UN PRODUCTO POR ID
document.getElementById("findProductForm").addEventListener("submit", async (event) => {
    event.preventDefault();

    const productIdInput = document.getElementById("productId");
    const button = document.getElementById("findProduct");
    const jwt = jwtInput.value.trim();
    const productId = productIdInput.value.trim();

    if (!jwt) {
        showStatus("Inicia sesión o registra un usuario para obtener el JWT.", true);
        return;
    }

    if (!productId || !Number.isInteger(Number(productId)) || Number(productId) < 1) {
        showStatus("Introduce un ID de producto válido.", true);
        return;
    }

    button.disabled = true;

    try {
        const result = await getData(
            `${API_URL}/api/v1/products/${encodeURIComponent(productId)}`,
            jwt
        );

        showResponse(result);
        showStatus(`Producto ${productId} obtenido correctamente.`);
    } catch (error) {
        handleError(error);
    } finally {
        button.disabled = false;
    }
});
