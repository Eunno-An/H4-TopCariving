import { Suspense, useEffect, useState } from 'react';
import { Outlet } from 'react-router-dom';
import { Flex } from '@components/common';
import { Footer, Header, NavBar } from '@components/myCar';
import styled from '@emotion/styled';
import { ErrorBoundary } from '@components/ErrorBoundary';
import { Loading } from '@components/common/Loading/Loader';

export const myCarUrl = [
  '/my-car/trim',
  '/my-car/trim/engine',
  '/my-car/trim/body-type',
  '/my-car/trim/traction',
  '/my-car/color',
  '/my-car/option',
  '/my-car/option/genuine',
  '/my-car/option/performance',
  '/my-car/complete',
] as string[];

const MyCar = () => {
  const [currentUrl, setCurrentUrl] = useState(
    window.location.pathname as string,
  );

  useEffect(() => {
    const handleLocationChange = () => {
      setCurrentUrl(window.location.pathname);
    };

    window.addEventListener('popstate', handleLocationChange);

    return () => {
      window.removeEventListener('popstate', handleLocationChange);
    };
  }, []);

  return (
    <Flex direction="column" align="center">
      <Header />
      <Margin height={60} />
      <NavBar currentUrl={currentUrl} />
      <Flex direction="column" width={1040}>
        <ErrorBoundary>
          <Suspense fallback={<Loading />}>
            <Outlet />
          </Suspense>
        </ErrorBoundary>
      </Flex>
      <Footer currentUrl={currentUrl} setCurrentUrl={setCurrentUrl} />
    </Flex>
  );
};

export default MyCar;

const Margin = styled(Flex)`
  flex-shrink: 0;
`;
