import { Flex, Text } from '@components/common';
import { Tag } from '@components/common/Tag';
import styled from '@emotion/styled';
import { theme } from '@styles/theme';
import leftBtn from '@assets/images/leftBtn.svg';
import rightBtn from '@assets/images/rightBtn.svg';
import { OptionCard } from '@components/common/OptionCard';

export const MyCarOptions = () => {
  return (
    <Flex direction="column" justify="flex-start" height="auto" gap={15}>
      {/* 옵션 상단 */}
      <Flex gap={39}>
        {/* 이미지 */}
        <Flex width={479} height={304}>
          <ImgContainer
            src="https://topcariving.s3.ap-northeast-2.amazonaws.com/power/driving_mode.jpg"
            alt=""
          />
        </Flex>
        {/* 옵션 Info */}
        <Flex direction="column">
          {/* 옵션 이름 / 가격 */}
          <OptionContainer>
            <Text typo="Heading1_Bold">컴포트 ||</Text>
            <Text typo="Heading2_Bold">+690,000 원</Text>
          </OptionContainer>
          {/* 옵션에대한 태그칩 */}
          <Flex
            width={507}
            height={108}
            padding="16px 0 0 0"
            direction="column"
            align="flex-start"
          >
            <Text>
              컴포트 ||
              <Text typo="Body3_Regular">
                에 대해 시승자들은 이런 후기를 남겼어요
              </Text>
            </Text>
            <Flex gap={4} justify="flex-start">
              <Tag desc="여름에 쓰기 좋아요☀️" />
              <Tag desc="옵션값 뽑았어요👍" />
              <Tag desc="편리해요☺️" />
            </Flex>
          </Flex>
          {/* 옵션 세부 설명 */}
          <InfoContainer>
            <InfoTitleContainer>
              <Flex justify="flex-start" gap={8}>
                <Flex
                  backgroundColor="Primary"
                  borderRadius="50%"
                  width={22}
                  height={22}
                  padding="6px"
                >
                  <Text palette="LightSand">06</Text>
                </Flex>
                <Text palette="Primary">헤드업 디스플레이</Text>
              </Flex>
              <Flex
                justify="flex-end"
                backgroundColor="DarkGray"
                borderRadius="13px"
                width="auto"
                padding="0 9px"
              >
                <Text palette="LightSand" typo="Caption_Regular">
                  6/6
                </Text>
              </Flex>
            </InfoTitleContainer>
            <Flex>
              <div>
                <img src={leftBtn} alt="" />
              </div>
              <Text typo="Body3_Regular" palette="Primary">
                초음파 센서를 통해 뒷좌석에 남아있는 승객의 움직임을 감지하여
                운전자에게 경고함으로써 부주의에 의한 유아 또는 반려 동물 등의
                방치 사고를 예방하는 신기술입니다.
              </Text>
              <div>
                <img src={rightBtn} alt="" />
              </div>
            </Flex>
          </InfoContainer>
        </Flex>
      </Flex>

      {/* 옵션 하단 */}
      <Flex direction="column" gap={21}>
        {/* 선택 항목 / 기본 포함 항목 */}
        <OptionMenu justify="flex-start" gap={23}>
          <Text typo="Body1_Medium">선택항목</Text>
          <Text typo="Heading4_Bold" palette="LightGray">
            기본 포함 품목
          </Text>
        </OptionMenu>
        {/* 옵션 카드 */}
        <Flex justify="flex-start" gap={6}>
          <OptionCard isSelected={0} idx={0} />
          <OptionCard isSelected={0} idx={1} />
          <OptionCard isSelected={0} idx={2} />
          <OptionCard isSelected={0} idx={2} />
          <OptionCard isSelected={0} idx={2} />
          <OptionCard isSelected={0} idx={2} />
        </Flex>
      </Flex>
    </Flex>
  );
};

const OptionMenu = styled(Flex)`
  border-bottom: 3px solid ${theme.palette.LightGray};
`;

const InfoTitleContainer = styled(Flex)`
  justify-content: space-between;
  border-bottom: 2px solid ${theme.palette.Primary};

  width: 443px;
  height: auto;

  padding: 20px 0 15px 0;
`;

const InfoContainer = styled(Flex)`
  flex-direction: column;
  width: 507px;
  height: 152px;

  border: 2px solid ${theme.palette.Primary};
  border-radius: 8px;

  background-color: rgba(0, 44, 95, 0.1);
`;

const OptionContainer = styled(Flex)`
  width: 507px;
  height: 44px;
  justify-content: space-between;

  border-bottom: 2px solid #545454;
`;

const ImgContainer = styled.img`
  width: 479px;
  height: 304px;
  border-radius: 4px;
`;
