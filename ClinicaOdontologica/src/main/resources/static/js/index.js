// Navegacion general:
const navItems = [
  { nav: "nav-odontologo", card: "card-odontologo" },
  { nav: "nav-paciente", card: "card-paciente" },
  { nav: "nav-turno", card: "card-turno" },
];

function navigation(itemSelected) {
  navItems.forEach((item) => {
    const navItem = document.getElementById(item.nav);
    const cardItem = document.getElementById(item.card);

    if (navItem == itemSelected) {
      navItem.classList.add("active");
      cardItem.style.display = "block";
    } else {
      navItem.classList.remove("active");
      cardItem.style.display = "none";
    }
  });
}

// Listar odontologos:
const tableBody = document.querySelector("#odontologosTable tbody");
function fetchOdontologos() {
  // listando los odontologos
  fetch(`/odontologo`)
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      // Limpiar el contenido actual de la tabla
      tableBody.innerHTML = "";

      // Insertar los datos en la tabla
      data.forEach((odontologo, index) => {
        const row = document.createElement("tr");

        row.innerHTML = `
                  <td>${odontologo.id}</td>
                  <td>${odontologo.nombre}</td>
                  <td>${odontologo.apellido}</td>
                  <td>${odontologo.nroMatricula}</td>
                  <td>
                    <button type="button" 
                            class="btn btn-primary btn-sm"
                            onclick="editOdontologo(${odontologo.id},'${odontologo.nombre}','${odontologo.apellido}','${odontologo.nroMatricula}')">
                      Modificar
                    </button>
                    <button class="btn btn-danger btn-sm" onclick="deleteOdontologo(${odontologo.id})">Eliminar</button>
                  </td>
                `;

        tableBody.appendChild(row);
      });
    })
    .catch((error) => {
      console.error("Error fetching data:", error);
    });
}
fetchOdontologos();

// Boton de agregar odontologo:
const odontologoForm = document.getElementById("staticBackdrop");
const titleModal = document.getElementById("staticBackdropLabel");
const modalType = document.getElementById("modal-type");
const cancelOdontologo = document.getElementById("cancel-odontologo");
const closeOdontologo = document.getElementById("btn-close-modal");
const odontologoModal = new bootstrap.Modal(odontologoForm);
// configura para agregar y abre modal odontologo:
document
  .getElementById("btn-add-odontologo")
  .addEventListener("click", function () {
    titleModal.innerHTML = "Agregar odontologo";
    modalType.value = "AO";
    odontologoModal.show();
  });

// Modificar odontologo:
function editOdontologo(id, nombre, apellido, nroMatricula) {
    titleModal.innerHTML = "Modificar odontologo";
    modalType.value = "MO";
    odontologoForm["orden"].value = id;
    odontologoForm["nombre"].value = nombre;
    odontologoForm["apellido"].value = apellido;
    odontologoForm["matricula"].value = nroMatricula;
    odontologoModal.show();
}

odontologoForm.addEventListener("submit", (e) => {
  e.preventDefault();
  switch (modalType.value) {
    case "AO":
      // Agregar un odontologo
      const newOdontologo = {
        nroMatricula: odontologoForm["matricula"].value,
        nombre: odontologoForm["nombre"].value,
        apellido: odontologoForm["apellido"].value,
      };
      fetch(`/odontologo`, {
        method: "post",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(newOdontologo),
      }).then((data) => {
        fetchOdontologos();
        closemodal();
        alert("Agregado exitosamente");
      });

      break;
    case "MO":
      // Modificar un odontologo
      const editOdontologo = {
        id: odontologoForm["orden"].value,
        nroMatricula: odontologoForm["matricula"].value,
        nombre: odontologoForm["nombre"].value,
        apellido: odontologoForm["apellido"].value,
      };
      fetch(`/odontologo`, {
        method: "put",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(editOdontologo),
      })
        .then((response) => response.json())
        .then((data) => {
          console.log(data);
          fetchOdontologos();
          closemodal();
          alert("Modificado exitosamente");
        });

      break;

    default:
        closemodal();
      break;
  }
});

// Evento de boton cancelar:
closeOdontologo.addEventListener("click", closemodal);
cancelOdontologo.addEventListener("click", closemodal);
function closemodal() {
  // Borrar datos:
  odontologoForm.reset();
  odontologoModal.hide();
}

// eliminar un odontologo
function deleteOdontologo(id) {
  const userConfirmed = confirm(
    "¿Estás seguro de que deseas eliminar este odontologo?"
  );

  if (userConfirmed) {
    fetch(`/odontologo/${id}`, {
      method: "delete",
    })
      .then((response) => response.json())
      .then((data) => {
        console.log(data);
        alert("Eliminado exitosamente");
        fetchOdontologos();
      });
  }
}

// Avisos de operaciones:
const toastTrigger = document.getElementById("liveToastBtn");
const toastLiveExample = document.getElementById("liveToast");

if (toastTrigger) {
  const toastBootstrap = bootstrap.Toast.getOrCreateInstance(toastLiveExample);
  toastTrigger.addEventListener("click", () => {
    toastBootstrap.show();
  });
}
