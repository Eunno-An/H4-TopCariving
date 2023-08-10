import { Flex, Text, Button } from '@components/common';
import styled from '@emotion/styled';
import { myCarFooterInterface } from '@interface/index';
import { myCarUrl } from '@pages/MyCar';
import { colorKey } from '@pages/MyCar/Color';
import { Dispatch, SetStateAction } from 'react';
import { useNavigate } from 'react-router-dom';

interface footerProps {
  currentUrl: string;
  setCurrentUrl: Dispatch<SetStateAction<string>>;
  footerInfo: myCarFooterInterface;
}

export const Footer = ({
  currentUrl,
  setCurrentUrl,
  footerInfo,
}: footerProps) => {
  const navigate = useNavigate();

  const onClickButton = (moveNum: number) => {
    const nextIdx = myCarUrl.indexOf(currentUrl) + moveNum;
    const nextUrl = myCarUrl[nextIdx];

    navigate(nextUrl);
    setCurrentUrl(nextUrl);
  };

  const colorInfo = [
    { text: '외장', key: 'exteriorColorResponses' },
    { text: '내장', key: 'interiorColorResponses' },
  ] as { text: string; key: colorKey }[];

  return (
    <Flex
      backgroundColor="Sand"
      height={108}
      borderRadius="16px 16px"
      padding="12px 36px 24px 36px"
      justify="center"
    >
      <Flex width={1280} gap={30}>
        <Flex>
          <Section width={175}>
            <Text typo="Body3_Regular" palette="DarkGray">
              트림
            </Text>
            {footerInfo.name.map((item, idx) => (
              <Text
                typo={idx ? 'Body3_Regular' : 'Heading4_Bold'}
                palette="Black"
                key={`trimModel_${idx}`}
              >
                {item}
              </Text>
            ))}
          </Section>
          <ColumnImg src="/image/page/myCar/columnLine.svg" />
          <Section width={220}>
            <Text typo="Body3_Regular" palette="DarkGray">
              선택 색상
            </Text>
            {colorInfo.map((color, idx) => (
              <Flex
                justify="start"
                gap={5}
                key={`color_${idx}
              `}
              >
                <Text typo="Body3_Medium" palette="Black">
                  {color.text}
                </Text>
                <Flex
                  backgroundColor="Primary"
                  borderRadius="100px"
                  width={16}
                  height={16}
                />
                <Text typo="Body3_Regular">{footerInfo.color[color.key]}</Text>
              </Flex>
            ))}
          </Section>
          <ColumnImg src="/image/page/myCar/columnLine.svg" />
          <Section width={290}>
            <Text typo="Body3_Regular" palette="DarkGray">
              선택 옵션
            </Text>
          </Section>
          <ColumnImg src="/image/page/myCar/columnLine.svg" />
          <Section width={170}>
            <Text typo="Body3_Regular" palette="DarkGray">
              예상 가격
            </Text>
            <Flex width="auto">
              <Text typo="Heading1_Bold" palette="Black">
                {`${footerInfo.price.toLocaleString('ko-KR')}`}
              </Text>
              <Text typo="Body3_Regular" palette="Black">
                원
              </Text>
            </Flex>
          </Section>
        </Flex>
        <Flex gap={7} width="auto">
          {myCarUrl.indexOf(currentUrl) != myCarUrl.length - 1 && (
            <>
              {myCarUrl.indexOf(currentUrl) !== 0 ? (
                <div onClick={() => onClickButton(-1)}>
                  <Button
                    width={121}
                    heightType="medium"
                    backgroundColor="White"
                  >
                    <Text palette="Primary" typo="Heading4_Bold">
                      이전
                    </Text>
                  </Button>
                </div>
              ) : (
                <Flex width={121}></Flex>
              )}

              <div onClick={() => onClickButton(+1)}>
                <Button
                  width={176}
                  heightType="medium"
                  backgroundColor="Primary"
                >
                  <Text palette="White" typo="Heading4_Bold">
                    다음 단계로
                  </Text>
                </Button>
              </div>
            </>
          )}
        </Flex>
      </Flex>
    </Flex>
  );
};

const Section = styled(Flex)`
  flex-direction: column;
  justify-content: flex-start;
  align-items: flex-start;

  gap: 6px;
  padding: 0 0 0 22px;
  box-sizing: border-box;
`;

const ColumnImg = styled.img`
  height: 100%;
`;
