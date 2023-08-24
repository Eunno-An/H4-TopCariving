import { ArchiveHeader, ArchiveNavbar } from '@components/archive';
import { MyCarList } from '@components/archive/myCariving/MyCarList';
import { Flex } from '@components/common';
import styled from '@emotion/styled';

export const MyCariving = () => {
  return (
    <Flex direction="column" justify="flex-start">
      <ArchiveHeader pageTitle={'마이카이빙'} />
      <ArchiveNavbar pageTitle={'마이카이빙'} />
      <TopMargin />
      <MyCarList />
    </Flex>
  );
};

const TopMargin = styled.div`
  height: 151px;
  flex-shrink: 0;
`;
