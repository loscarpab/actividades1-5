package com.ccormor392.actividades1_5.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly

/*
Actividad 1:
Hacer que el texto del botón muestre "Cargar perfil", pero cambie a "Cancelar"
cuando se muestre la línea de progreso... Cuando pulsemos "Cancelar" vuelve al texto por defecto.
*/
@Preview(showBackground = true)
@Composable
fun Actividad1() {
    var showLoading by rememberSaveable { mutableStateOf(false) }
    var buttonText by rememberSaveable { mutableStateOf("Cargar Perfil") }

    Column(
        Modifier
            .padding(24.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (showLoading) {
            buttonText = "Cancelar"
            CircularProgressIndicator(
                color = Color.Red,
                strokeWidth = 10.dp
            )
            LinearProgressIndicator(
                modifier = Modifier.padding(top = 32.dp),
                color = Color.Red,
                trackColor = Color.LightGray
            )
        }
        else{
            buttonText = "Cargar Perfil"
        }

        Button(
            onClick = { showLoading = !showLoading }
        ) {
            Text(text = buttonText)
        }
    }
}

/*
Actividad 2:
Modifica ahora también que se separe el botón de la línea de progreso 30 dp,
pero usando un estado... es decir, cuando no sea visible no quiero que tenga la separación
aunque no se vea.
*/
@Preview(showBackground = true)
@Composable
fun Actividad2() {
    var showLoading by rememberSaveable { mutableStateOf(false) }
    var buttonText by rememberSaveable { mutableStateOf("Cargar Perfil") }
    var separacion by rememberSaveable { mutableStateOf(0)}

    Column(
        Modifier
            .padding(24.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (showLoading) {
            buttonText = "Cancelar"
            separacion = 30
            CircularProgressIndicator(
                color = Color.Red,
                strokeWidth = 10.dp
            )
            LinearProgressIndicator(
                modifier = Modifier.padding(top = 32.dp),
                color = Color.Red,
                trackColor = Color.LightGray
            )
        }
        else {
            buttonText = "Cargar Perfil"
            separacion = 0
        }

        Button(
            onClick = { showLoading = !showLoading },
            modifier = Modifier.padding(top = separacion.dp)
        ) {
            Text(text = buttonText)
        }
    }
}

/*
Actividad 3:
- Separar los botones entre ellos 10 dp, del indicador de progreso 15 dp y centrarlos horizontalmente.
- Cuando se clique el botón Incrementar, debe añadir 0.1 a la propiedad progress y quitar 0.1
  cuando se pulse el botón Decrementar.
- Evitar que nos pasemos de los márgenes de su propiedad progressStatus (0-1)
*/
@Preview(showBackground = true)
@Composable
fun Actividad3() {
    var progreso by rememberSaveable{ mutableStateOf(0f)}
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LinearProgressIndicator(progress = progreso)

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(onClick = {if (progreso < 1f) progreso+=0.1f }, modifier = Modifier.padding(top = 15.dp)) {
                Text(text = "Incrementar")
            }
            Button(onClick = { if (progreso > 0f) progreso-=0.1f}, modifier = Modifier.padding(start = 10.dp, top = 15.dp)) {
                Text(text = "Reducir")
            }
        }
    }
}


/*
Actividad 4:
Sitúa el TextField en el centro de la pantalla y haz que reemplace el valor de una coma por un punto
y que no deje escribir más de un punto decimal...
*/
@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Actividad4() {
    var myVal by rememberSaveable { mutableStateOf("") }
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()){
        TextField(
            value = myVal,
            onValueChange = {
                            var texto = it
                            if (texto.contains(","))
                                texto = it.replace(',', '.')
                            if (texto.count{ c -> c == '.'} <= 1)
                                myVal = texto
                            },
            label = { Text(text = "Importe")}
        )
    }

}


/*
Actividad 5:
Haz lo mismo, pero elevando el estado a una función superior y usando un componente OutlinedTextField
al que debes añadir un padding alrededor de 15 dp y establecer colores diferentes en los bordes
cuando tenga el foco y no lo tenga.
A nivel funcional no permitas que se introduzcan caracteres que invaliden un número decimal.
*/
@Preview(showBackground = true)
@Composable
fun PreviewActividad5(){
    var importe by rememberSaveable{ mutableStateOf("")}
    Actividad5(importe, onValueChange = { NewValue ->
        var texto = NewValue
        if (texto.contains(","))
            texto = NewValue.replace(',', '.')
        if (texto.count{ c -> c == '.'} <= 1 && texto.isDigitsOnly())
            importe = texto
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Actividad5(value: String,
              onValueChange: (String) -> Unit) {

    var color by rememberSaveable{ mutableStateOf(Color.Green)}

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()){
        OutlinedTextField(
            value = value,
            onValueChange = { onValueChange(it) },
            modifier = Modifier
                .padding(15.dp)
                .border(5.dp, color)
                .onFocusChanged { color = if (it.isFocused) Color.Green else Color.Red }
                .focusable(true),
            label = { Text(text = "Importe") }
        )
    }
}

