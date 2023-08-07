import { defineConfig } from "vite";
import react from "@vitejs/plugin-react-swc";

export default defineConfig({
  plugins: [
    react({
      jsxImportSource: "@emotion/react",
      babel: {
        plugins: ["@emotion/babel-plugin"],
      },
    }),
  ],
  resolve: {
    alias: {
      '@interface': '/src/interface',
      '@assets': '/src/assets',
      '@components': '/src/components',
      '@pages': '/src/pages',
      '@routes': '/src/routes',
      '@styles': '/src/styles'
    }
  },
});
