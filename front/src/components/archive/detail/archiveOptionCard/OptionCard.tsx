import { Flex, Tag, Text } from '@components/common';
import styled from '@emotion/styled';
import { theme } from '@styles/theme';
import { OptionInfoInterface } from '..';

export const OptionCard = ({ info }: { info: OptionInfoInterface }) => {
  return (
    <OptionDetailCard>
      <OptionTop direction="column" align="flex-start">
        <OptionImg src={info.photoUrl} alt="" />
        <Text typo="Heading3_Medium" margin="20px 0 9px 0">
          {info.optionName}
        </Text>
      </OptionTop>
      {info.option && (
        <Text typo="Body2_Medium" palette="Primary" margin="0 0 10px 0">
          {info.option.join(' | ')}
        </Text>
      )}
      {info.review && (
        <Text typo="Body3_Regular" margin="0 0 20px 0">
          {info.review}
        </Text>
      )}

      <Flex gap={8}>
        {info.tags.map((it) => (
          <Tag desc={it} />
        ))}
      </Flex>
    </OptionDetailCard>
  );
};

const OptionTop = styled(Flex)`
  width: 314px;
  flex-direction: column;
  align-items: flex-start;

  border-bottom: 1px solid ${theme.palette.LightGray};

  margin-bottom: 12px;
`;

const OptionDetailCard = styled(Flex)`
  width: 335px;
  height: auto;
  padding: 15px 15px 30px 15px;
  flex-direction: column;
  border: 2px solid ${theme.palette.Sand};

  border-radius: 8px;
`;

const OptionImg = styled.img`
  width: 314px;
  height: 130px;
  border-radius: 8px;
`;
