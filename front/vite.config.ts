import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react-swc';

export default defineConfig({
  define: {
    'process.env.VITE_ENV': JSON.stringify(
      process.env.VITE_ENV || 'production',
    ),
  },
  plugins: [
    react({
      jsxImportSource: '@emotion/react',
      babel: {
        plugins: ['@emotion/babel-plugin'],
      },
    }),
  ],
  resolve: {
    alias: {
      '@contexts': '/src/contexts',
      '@interface': '/src/interface',
      '@assets': '/src/assets',
      '@components': '/src/components',
      '@pages': '/src/pages',
      '@routes': '/src/routes',
      '@styles': '/src/styles',
      '@utils': '/src/utils',
    },
  },
});
