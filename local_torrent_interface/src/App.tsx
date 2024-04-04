import React from 'react';
import logo from './logo.svg';
import './App.css';
import { ChakraProvider, ColorModeScript } from '@chakra-ui/react';
import theme from './theme';
import DrawerExample from './components/Drawer';

function App() {
  return (
    <ChakraProvider theme={theme}>
      <ColorModeScript initialColorMode={theme.config.initialColorMode}/>
      <DrawerExample/>
    </ChakraProvider>
  );
}

export default App;
