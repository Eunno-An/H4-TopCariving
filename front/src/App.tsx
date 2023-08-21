import styled from '@emotion/styled';
import { AlertModalWrapper } from '@components/common';
import { AlertProvider, MyCarProvider } from './contexts';
import { router } from './routes/router';
import { RouterProvider } from 'react-router-dom';
import Toast from '@components/common/Toast';
import { ToastProvider } from '@contexts/ToastContext';
import React from 'react';

interface AppProviderProps {
  contexts: React.ElementType[];
  children: React.ReactNode;
}

function App() {
  const AppProvider = ({ contexts, children }: AppProviderProps) =>
    contexts.reduce(
      (prev: React.ReactNode, context: React.ElementType) =>
        React.createElement(context, {
          children: prev,
        }),
      children,
    );

  return (
    <AppProvider contexts={[ToastProvider, AlertProvider, MyCarProvider]}>
      <Container>
        <RouterProvider router={router} />
        <AlertModalWrapper />
        <Toast />
      </Container>
    </AppProvider>
  );
}

const Container = styled.div`
  position: relative;
  width: 100vw;
  height: 100vh;
`;

export default App;
