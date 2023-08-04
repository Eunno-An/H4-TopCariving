import styled from '@emotion/styled';
import { router } from './routes/router';
import { RouterProvider } from 'react-router-dom';

function App() {
  return (
    <Container>
      <RouterProvider router={router} />
    </Container>
  );
}

const Container = styled.div`
  width: 100vw;
  height: 100vh;
`;

export default App;
