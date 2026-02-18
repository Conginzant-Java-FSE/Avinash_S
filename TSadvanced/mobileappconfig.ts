
const config = {
  theme: "dark",
  version: 2
} as const;
type Theme = typeof config.theme;
let currentTheme: Theme = "dark";
console.log("Current theme:", currentTheme);