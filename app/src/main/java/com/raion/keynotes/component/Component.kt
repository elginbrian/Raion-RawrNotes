package com.raion.keynotes.component

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raion.keynotes.R
import com.raion.keynotes.model.NoteClass
import com.raion.keynotes.model.NoteItem
import com.raion.keynotes.screen.RaionAPIViewModel

@Composable
fun Notes(
    viewModel: RaionAPIViewModel,
    loadingValue: (Boolean) -> Unit
){
    val notes = viewModel.getNote.value.data
    var currentToken: String

    viewModel.getNote.value.loading?.let { loadingValue(it) }

    if(viewModel.getNote.value.loading == true){
        CircularProgressIndicator()
        Log.d("Loading", "Note: Loading....")

    } else {
        Log.d("Notes at Component.kt Log.d", "Note: Message -> ${notes?.message} | Count: -> ${notes?.count}")
        Log.d("Notes at Component.kt Log.d", "Note: Data -> ${notes?.data.toString()}")
        //Log.d("Notes at Component.kt Log.d", "Current token: $currentToken")
    }
}

@Composable
fun UserDetail(
    viewModel: RaionAPIViewModel,
    loadingValue: (Boolean) -> Unit
){
    val userDetail = viewModel.getUserDetail.value.data
    var userId: String = ""
    var userName: String = ""
    var password: String = ""
    var salt: String = ""

    viewModel.getUserDetail.value.loading?.let { loadingValue(it) }

    if(viewModel.getNote.value.loading == true){
        CircularProgressIndicator()
        Log.d("Loading", "UserDetail: Loading....")

    } else {
        Log.d("UserDetail at Component.kt Log.d", "User Detail: Message -> ${userDetail?.message}")
        Log.d("UserDetail at Component.kt Log.d", "User Detail: Data -> ${userDetail?.data.toString()}")
    }
}

@Composable
fun NoteCard(
    noteItem: NoteItem,
    isPinned: Boolean,
    trigger: (String) -> Unit
){
    var noteColor: Color = Color.White
    if(noteItem.description.contains("#NoteColorRed")){
        noteColor = Color(250, 175, 175)
    } else if(noteItem.description.contains("#NoteColorGreen")){
        noteColor = Color(220, 250, 175)
    } else if (noteItem.description.contains("#NoteColorBlue")){
        noteColor = Color(175, 210, 250)
    } else if (noteItem.description.contains("#NoteColorViolet")){
        noteColor = Color(210, 175, 250)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(
                start = 10.dp,
                end = 10.dp,
                top = 15.dp,
                bottom = 5.dp
            )
            .clickable { trigger(noteItem.noteId) },
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardDefaults.cardColors(noteColor),
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp),
                colors = CardDefaults.cardColors(Color(30,30,30, 255))
            ) {
                Row(modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 15.dp, end = 15.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = noteItem.title, fontSize = 18.sp ,fontWeight = FontWeight(500), color = Color.White, maxLines = 1)

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if(isPinned == true){
                            Box(modifier = Modifier
                                .width(18.dp)
                                .height(18.dp)){
                                Icon(painter = painterResource(id = R.drawable.thumbtack), contentDescription = "pin", tint = Color.White)
                            }
                        }
                    }
                }
            }

            Column(modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            ) {
                Text(text = noteItem.description, fontSize = 12.sp, maxLines = 7, textAlign = TextAlign.Justify, lineHeight = 13.sp)
            }
        }
    }
}

@Composable
fun DownloadedNoteCard(
    noteItem: NoteClass,
    isPinned: Boolean,
    trigger: (String) -> Unit
){
    var noteColor: Color = Color.White
    if(noteItem.description.contains("#NoteColorRed")){
        noteColor = Color(250, 175, 175)
    } else if(noteItem.description.contains("#NoteColorGreen")){
        noteColor = Color(220, 250, 175)
    } else if (noteItem.description.contains("#NoteColorBlue")){
        noteColor = Color(175, 210, 250)
    } else if (noteItem.description.contains("#NoteColorViolet")){
        noteColor = Color(210, 175, 250)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(
                start = 10.dp,
                end = 10.dp,
                top = 15.dp,
                bottom = 5.dp
            )
            .clickable { trigger(noteItem.noteId) },
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardDefaults.cardColors(noteColor)
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp),
                colors = CardDefaults.cardColors(Color(30,30,30, 255))
            ) {
                Row(modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 15.dp, end = 15.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = noteItem.title, fontSize = 18.sp ,fontWeight = FontWeight(500), color = Color.White, maxLines = 1)

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if(isPinned == true){
                            Box(modifier = Modifier
                                .width(18.dp)
                                .height(18.dp)){
                                Icon(painter = painterResource(id = R.drawable.thumbtack), contentDescription = "pin", tint = Color.White)
                            }
                        }
                    }
                }
            }

            Column(modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            ) {
                Text(text = noteItem.description, fontSize = 12.sp, maxLines = 7, textAlign = TextAlign.Justify, lineHeight = 13.sp)
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun RaionTextField(
    modifier: Modifier = Modifier.fillMaxWidth(),
    text: String,
    label: String,
    maxLine: Int = 1,
    onTextChange: (String) -> Unit,
    onImeAction: () -> Unit = {},
    keyboardType: KeyboardType = KeyboardType.Text
){
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = text,
        onValueChange = onTextChange,
        colors = TextFieldDefaults.textFieldColors(textColor = MaterialTheme.colorScheme.primary),
        maxLines = maxLine,
        label = { Text(text = label) },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = {
            onImeAction
            keyboardController?.hide()
        }),

        modifier = Modifier
    )
}

@Composable
fun BarButton(
    text: String = "Lorem Ipsum",
    color: Color = MaterialTheme.colorScheme.primary,
    whenWidgetClicked: (String) -> Unit = {},
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable { whenWidgetClicked("trigger") },
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(color),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = text, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
        }
    }
}

@Composable
fun TransparentTextField(
    text: String = "",
    hint: String = "",
    modifier: Modifier = Modifier,
    isHintVisible: Boolean = true,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle = TextStyle(),
    singleLine: Boolean = false,
    onFocusChange: (FocusState) -> Unit
){
    Box(
        modifier = Modifier
    ) {
        BasicTextField(
            value = text,
            onValueChange = onValueChange,
            singleLine = singleLine,
            textStyle = textStyle,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { onFocusChange(it) }
        )
        if(isHintVisible){
            Text(text = hint, style = textStyle, color = Color.DarkGray)
        }
    }
}

@Composable
fun NoteColorBar(
    colorFlag: String,
    newNoteDescription: String,
    returnNoteDescription: (String) -> Unit,
    returnColorFlag: (String) -> Unit
){
    var newNoteDescription = newNoteDescription
    var colorFlag = colorFlag
    Card(
        modifier = Modifier
            .fillMaxWidth(0.92f)
            .height(55.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(Color(51, 47, 51))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Note Color: ",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )

            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    modifier = Modifier
                        .width(35.dp)
                        .height(35.dp)
                        .clickable {
                            colorFlag = "1"
                            returnColorFlag(colorFlag)
                            newNoteDescription =
                                newNoteDescription.replace(
                                    "#NoteColorRed",
                                    ""
                                )
                            newNoteDescription =
                                newNoteDescription.replace(
                                    "#NoteColorGreen",
                                    ""
                                )
                            newNoteDescription =
                                newNoteDescription.replace(
                                    "#NoteColorBlue",
                                    ""
                                )
                            newNoteDescription =
                                newNoteDescription.replace(
                                    "#NoteColorViolet",
                                    ""
                                )
                            newNoteDescription = newNoteDescription + "\n#NoteColorRed"
                            returnNoteDescription(newNoteDescription)
                        },
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(
                        Color(
                            250,
                            110,
                            80
                        )
                    ),
                    border =
                    if (colorFlag == "1") {
                        BorderStroke(4.dp, Color.White)
                    } else {
                        null
                    }
                ) {}

                Spacer(modifier = Modifier.padding(5.dp))

                Card(
                    modifier = Modifier
                        .width(35.dp)
                        .height(35.dp)
                        .clickable {
                            colorFlag = "2"
                            returnColorFlag(colorFlag)
                            newNoteDescription =
                                newNoteDescription.replace(
                                    "#NoteColorRed",
                                    ""
                                )
                            newNoteDescription =
                                newNoteDescription.replace(
                                    "#NoteColorGreen",
                                    ""
                                )
                            newNoteDescription =
                                newNoteDescription.replace(
                                    "#NoteColorBlue",
                                    ""
                                )
                            newNoteDescription =
                                newNoteDescription.replace(
                                    "#NoteColorViolet",
                                    ""
                                )
                            newNoteDescription = newNoteDescription + "\n#NoteColorGreen"
                            returnNoteDescription(newNoteDescription)
                        },
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(
                        Color(
                            185,
                            250,
                            80
                        )
                    ),
                    border =
                    if (colorFlag == "2") {
                        BorderStroke(4.dp, Color.White)
                    } else {
                        null
                    }
                ) {}

                Spacer(modifier = Modifier.padding(5.dp))

                Card(
                    modifier = Modifier
                        .width(35.dp)
                        .height(35.dp)
                        .clickable {
                            colorFlag = "3"
                            returnColorFlag(colorFlag)
                            newNoteDescription =
                                newNoteDescription.replace(
                                    "#NoteColorRed",
                                    ""
                                )
                            newNoteDescription =
                                newNoteDescription.replace(
                                    "#NoteColorGreen",
                                    ""
                                )
                            newNoteDescription =
                                newNoteDescription.replace(
                                    "#NoteColorBlue",
                                    ""
                                )
                            newNoteDescription =
                                newNoteDescription.replace(
                                    "#NoteColorViolet",
                                    ""
                                )
                            newNoteDescription = newNoteDescription + "\n#NoteColorBlue"
                            returnNoteDescription(newNoteDescription)
                        },
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(
                        Color(
                            80,
                            120,
                            250
                        )
                    ),
                    border =
                    if (colorFlag == "3") {
                        BorderStroke(4.dp, Color.White)
                    } else {
                        null
                    }
                ) {}

                Spacer(modifier = Modifier.padding(5.dp))

                Card(
                    modifier = Modifier
                        .width(35.dp)
                        .height(35.dp)
                        .clickable {
                            colorFlag = "4"
                            returnColorFlag(colorFlag)
                            newNoteDescription =
                                newNoteDescription.replace(
                                    "#NoteColorRed",
                                    ""
                                )
                            newNoteDescription =
                                newNoteDescription.replace(
                                    "#NoteColorGreen",
                                    ""
                                )
                            newNoteDescription =
                                newNoteDescription.replace(
                                    "#NoteColorBlue",
                                    ""
                                )
                            newNoteDescription =
                                newNoteDescription.replace(
                                    "#NoteColorViolet",
                                    ""
                                )
                            newNoteDescription = newNoteDescription + "\n#NoteColorViolet"
                            returnNoteDescription(newNoteDescription)
                        },
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(
                        Color(
                            170,
                            80,
                            250
                        )
                    ),
                    border =
                    if (colorFlag == "4") {
                        BorderStroke(4.dp, Color.White)
                    } else {
                        null
                    }
                ) {}
            }
        }
    }
}


