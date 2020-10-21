'use strict';
import { TouchableWithoutFeedback } from 'react-native'
import React from 'react'
import {UI} from '@hyext/hy-ui'
import '../assets/css/SelectNum.hycss'
const { Text, View } = UI;

const SelectNum = ({num, changeNum}) => {
  return (
    <View className="container">
        <View className="selectNum">
            <Text className="text">{num}个</Text>
            <TouchableWithoutFeedback  onPress={()=>changeNum(-1)}>
                <View className="left"><Text className="leftSymbol">-</Text></View>
            </TouchableWithoutFeedback>
            <TouchableWithoutFeedback   onPress={()=>changeNum(1)}>
                <View className="right"><Text className="rightSymbol">+</Text></View>
            </TouchableWithoutFeedback>
        </View>
    </View>
  )
}

export default SelectNum