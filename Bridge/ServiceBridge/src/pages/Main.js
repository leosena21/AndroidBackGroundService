import React, { useState, useEffect } from 'react';
import { View, TextInput, StyleSheet,TouchableOpacity, Text, NativeModules, NativeEventEmitter } from 'react-native';

import SendIntentAndroid from 'react-native-send-intent';

// import { Container } from './styles';

const {ToastModule} = NativeModules;
const { CalendarManager } = NativeModules;
const calendarManagerEmitter = new NativeEventEmitter(CalendarManager);

export default function pages() {

  function listening(){
    const subscription = calendarManagerEmitter.addListener(
      'onStop',
      (e) => {
        console.log(e.test)
        subscription.remove();
      }
    );
  }
 
  const [text, setText] = useState('');

  function start(){
    ToastModule.doBindService();
  }
  
  function stop(){
   SendIntentAndroid.openApp("com.example.serviceback",{
    "reciveApp": "0",
   }).then(wasOpened => {});
  }

  function callJava(){
    listening();
    ToastModule.withMsg(text);
   // ToastModule.showText('This is Android', ToastModule.LENGTH_SHORT);
  }

  return (
    <View style={styles.container}>
      <TextInput
        keyboardType = "numeric"
        style={styles.input}
        placeholder = "Digite o estado do serviço"
        onChangeText={setText}
        value={text}
      />

      <TouchableOpacity onPress={start}  style={styles.bt_start}>
        <Text style={styles.text} >Start Server</Text>
      </TouchableOpacity>

      <TouchableOpacity  onPress={stop} style={styles.bt_stop}>
        <Text style={styles.text} >Stop Server</Text>
      </TouchableOpacity>

      <TouchableOpacity  onPress={callJava} style={styles.bt_start}>
        <Text style={styles.text} >Chamar Java</Text>
      </TouchableOpacity>
        
    </View>
  );
}

const styles = StyleSheet.create({

  container: {
    flex: 1,
    alignItems: 'center',
    marginTop: 100,
    margin:10,
    padding:20,
    marginHorizontal: 16,
  },

  text: {
    textAlign: 'center',
    fontWeight: 'bold',
    fontSize: 13,
    marginVertical: 8,
    color: '#FFF',
  },
  

  input: {
    height: 46,
    alignSelf: 'stretch',
    backgroundColor: '#FFF',
    borderWidth: 1,
    borderColor: '#ddd',
    borderRadius: 4,
    marginTop: 20,
    paddingHorizontal: 15,
  },

  bt_start: {
    height: 46,
    alignSelf: 'stretch',
    backgroundColor: '#2AB61F',
    borderRadius: 4,
    marginTop: 30,
    justifyContent: 'center',
    alignItems: 'center',
  },

  bt_stop: {
    height: 46,
    alignSelf: 'stretch',
    backgroundColor: '#DF4723',
    borderRadius: 4,
    marginTop: 30,
    justifyContent: 'center',
    alignItems: 'center',
  },

});
