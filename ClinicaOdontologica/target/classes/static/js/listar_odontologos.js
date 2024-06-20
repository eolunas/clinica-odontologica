const tableBody = document.querySelector("#odontologosTable tbody");
const odontologoForm = document.getElementById('staticBackdrop');
const cancelOdontologo = document.getElementById('cancel-odontologo');

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
                          data-bs-toggle="modal" 
                          data-bs-target="#staticBackdrop">
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

  // modificar un odontologo
  function editOdontologo(id, nombre, apellido, nroMatricula) {

  }

  odontologoForm.addEventListener('submit', (e) => {
    e.preventDefault();
    // Instanciar el modal con Bootstrap
    var modalInstance = new bootstrap.Modal(odontologoForm);
    modalInstance.hide();
  })

  cancelOdontologo.addEventListener('click', (e) => {
    // Borrar datos:
    odontologoForm['nombre'].value = '';
    odontologoForm['apellido'].value = '';
    odontologoForm['matricula'].value = '';
  })

  // eliminar un odontologo
  function deleteOdontologo(id) {
    fetch(`/odontologo/${id}`, {
      method: 'delete'
    })
    .then((response) => response.json())
    .then(data => {
        console.log(data)
        alert('Eliminado exitosamente');
        fetchOdontologos();
    }
    )
  }

fetchOdontologos();
