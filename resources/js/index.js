let cursor
let tipID = 'tip'

let Cursor = function (element) {
  this.element = element
  this.isDeleting = true
  this.currentText = element.textContent

  this.loopCursor = 0
  this.showCursor = true

  this.dialog = 'welcome'

  this.quoteNum = -1
  this.quotes = {
    'welcome': [
      'Buscad los siguientes números de la tabla periódica y decidme el nombre que necesito para continuar '
    ],
    'match': [
      '¡Correcto! Ya nos hemos saludado, ¡ahora intenta descrifrar el resto! ',
      '¡Muy bien! Ahora una fácil ',
      'Y por fin la gran pregunta... ¿Quién es? ',
      'La libreta que os interesa se abre con el número 161. Ha sido un placer conoceros '
    ],
    'error': [      
      'Me advirtieron de que los humanos no erais muy bueno en química ',
      'Si vuelves a fallar, tendré que asegurarme de que no eres un robot ',
      'Respuesta incorrecta. Fíjate de nuevo en la pista que te he dejado ',
      'Venga, que no es tan difícil, inténtalo de nuevo ',
      'Creo que no hay química entre nosotros '
    ]
  }

  this.currentKey = 0
  this.values = [ 'hola', 'friki', 'es', 'monica' ]
  this.keys = ['1 8 57', '87 53 19 53', '99', '42 7 53 20', '♥ ¡Perfecto! ♥']

  document.getElementById(tipID).innerText = this.keys[this.currentKey]

  this.tick()
}

Cursor.prototype.tick = function () {
  if (this.isDeleting) {
    this.currentText = this.currentText.substring(0, this.currentText.length - 1)
  } else {
    this.currentText = this.quotes[this.dialog][this.quoteNum].substring(0, this.currentText.length + 1)
  }

  this.element.innerHTML = this.currentText

  if (this.showCursor) {
    this.element.innerHTML = this.element.innerHTML.substring(0, this.element.textContent.length - 1) + '█'
  } else {
    this.element.innerHTML = this.element.innerHTML.substring(0, this.element.textContent.length - 1) + ' '
  }

  this.loopCursor = this.loopCursor === 6 ? 0 : this.loopCursor + 1
  if (this.loopCursor === 0) this.showCursor = !this.showCursor

  let that = this
  let delta = 160 - Math.random() * 100
  if (this.isDeleting) { delta /= 2 }

  if (this.isDeleting && this.currentText === '') {
    if(this.dialog === 'match') this.quoteNum = this.currentKey - 1
    else this.quoteNum = Math.floor(Math.random() * this.quotes[this.dialog].length)
    this.isDeleting = false
  }

  setTimeout(function () {
    that.tick()
  }, delta)
}

Cursor.prototype.parseData = function (data) {
  let _data = data.toLowerCase().replace(new RegExp(' ', 'g'), '')

  console.log(this.currentKey)
  if (_data === 'adios' || _data === 'salir' || _data === 'cerrar') {
    require('electron').remote.getCurrentWindow().close()
  } else if (_data == 'reiniciar' || this.currentKey === this.values.length) {
    require('electron').remote.getCurrentWindow().reload()
  } else if (_data === this.values[this.currentKey]) {
    this.currentKey++
    this.dialog = 'match'
    document.getElementById(tipID).innerText = this.keys[this.currentKey]
    this.isDeleting = true
  } else {
    this.dialog = 'error'
    this.isDeleting = true
  }
}

function Terminal () {
  let tip = document.getElementById('tip')
  let terminal = document.getElementById('terminal')
  setTimeout(function () {
    cursor = new Cursor(terminal)
  }, 1500) // Tiempo después del blink de la pantalla
}

document.getElementById('formulario').onsubmit = function (event) {
  event.preventDefault()
  if (cursor) {
    cursor.parseData(document.getElementById('password').value)
    document.getElementById('password').value = ''
  }
  return false
}

Terminal()
