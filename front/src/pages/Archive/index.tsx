import { ArchiveHeader } from '@components/archive/ArchiveHeader';
import { ArchiveNavbar } from '@components/archive/ArchiveNavbar';
import { Flex } from '@components/common';
import { Outlet } from 'react-router-dom';

export const Archive = () => {
  return (
    <Flex direction="column" justify="flex-start">
      <ArchiveHeader />
      <ArchiveNavbar />
      <Outlet />
    </Flex>
  );
};
