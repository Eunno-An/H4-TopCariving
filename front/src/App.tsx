import styled from '@emotion/styled';
import { AlertModalWrapper } from '@components/common';
import { AlertProvider, MyCarProvider } from './contexts';
import { router } from './routes/router';
import { RouterProvider } from 'react-router-dom';

function App() {
  return (
    <AlertProvider>
      <MyCarProvider>
        <Container>
          <RouterProvider router={router} />
          <AlertModalWrapper />
        </Container>
      </MyCarProvider>
    </AlertProvider>
  );
}

const Container = styled.div`
  position: relative;
  width: 100vw;
  height: 100vh;
`;

export default App;
