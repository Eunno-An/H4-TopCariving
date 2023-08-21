import { ErrorBoundary } from '@components/ErrorBoundary';
import { ArchiveHeader } from '@components/archive/ArchiveHeader';
import { ArchiveNavbar } from '@components/archive/ArchiveNavbar';
import { Flex } from '@components/common';
import styled from '@emotion/styled';
import { Outlet } from 'react-router-dom';

export const Archive = () => {
  return (
    <Flex direction="column" justify="flex-start">
      <ArchiveHeader />
      <TopMargin />
      <ArchiveNavbar />
      <ErrorBoundary>
        <Outlet />
      </ErrorBoundary>
    </Flex>
  );
};

export const TopMargin = styled.div`
  height: 60px;
  flex-shrink: 0;
`;
