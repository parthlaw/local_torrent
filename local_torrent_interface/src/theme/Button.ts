const Button = {
  // Base styles for the button
  baseStyle: {
    fontWeight: "bold",
    borderRadius: "md", // Adjust border radius as needed
  },
  defaultProps: {
        size: 'lg', // default is md
        variant: 'sm', // default is solid
        colorScheme: 'black', // default is gray
      },
  // Sizes for the button
  sizes: {
    sm: {
      fontSize: "12px",
      padding: "10px 16px",
    },
    md: {
      fontSize: "14px",
      padding: "12px 20px",
    },
    lg: {
      fontSize: "16px",
      padding: "14px 24px",
    },
  },
  // Variants for the button
  variants: {
    primary: {
      bg: "black",
      color: "white",
      _hover: {
        bg: "blue.600",
      },
      _active: {
        bg: "blue.700",
      },
    },
    secondary: {
      bg: "gray.300",
      color: "gray.700",
      _hover: {
        bg: "gray.400",
      },
      _active: {
        bg: "gray.500",
      },
    },
  },
};

export default Button;
