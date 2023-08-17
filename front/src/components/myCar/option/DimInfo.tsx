import { Flex, Text } from '@components/common';
import styled from '@emotion/styled';
import { theme } from '@styles/theme';

export const DimInfoMsg = ({ desc }: { desc: string }) => {
  return (
    <DimInfo>
      <Text>*</Text>
      <Text>{desc}</Text>
    </DimInfo>
  );
};

const DimInfo = styled(Flex)`
  height: auto;
  justify-content: flex-start;
  align-items: flex-start;
  gap: 5px;
  color: ${theme.palette.White};
  ${theme.typo.Body4_Regular}

  z-index: 10;
`;
