import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';
import tsConfigPaths from 'vite-tsconfig-paths';
import EnvironmentPlugin from 'vite-plugin-environment';

const path = require('path');
export default defineConfig({
  plugins: [react(), tsConfigPaths(), EnvironmentPlugin('all')],
  resolve: {
    alias: {
      '@app': path.resolve(__dirname, './src'),
    },
  },
});
