let cursor

let Cursor = function (element) {
  this.element = element
  this.isDeleting = true
  this.currentText = element.textContent

  this.loopCursor = 0
  this.showCursor = true

  this.quoteNum = -1
  this.quotes = {
    'dialog': [
      'Sartu identifikazio zenbakia '
    ]
  }
  this.tick()
}

Cursor.prototype.tick = function () {
  if (this.isDeleting) {
    this.currentText = this.currentText.substring(0, this.currentText.length - 1)
  } else {
    this.currentText = this.quotes.dialog[this.quoteNum].substring(0, this.currentText.length + 1)
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
  let delta = 200 - Math.random() * 100
  if (this.isDeleting) { delta /= 2 }

  if (this.isDeleting && this.currentText === '') {
    this.quoteNum++
    this.isDeleting = false
  }

  setTimeout(function () {
    that.tick()
  }, delta)
}

Cursor.prototype.parseData = function (data) {
  console.log(data)
  if (data.toLowerCase() === 'agur') {
    require('electron').remote.getCurrentWindow().close()
  }
}

function Terminal () {
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
