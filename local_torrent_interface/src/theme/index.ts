import { extendTheme, ThemeConfig } from '@chakra-ui/react'
import Button from './Button'
// 2. Add your color mode config
const config: ThemeConfig = {
  initialColorMode: 'dark',
  useSystemColorMode: false,
}
const options = {
  components:{
    Button
  }
}
// 3. extend the theme
const theme = extendTheme({ ...options,config })

export default theme
