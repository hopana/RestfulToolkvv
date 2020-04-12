package com.github.aloxc.plugin.restfulvv;

import java.math.BigDecimal;

public enum EvalRankEnum {

    /**
     * 差
     */
    POOR(1, "差",0),
    /**
     * 中
     */
    FAIR(2, "中",2),
    /**
     * 良
     */
    GOOD(3, "良",3.5),
    /**
     * 优
     */
    EXCELLENT(4, "优",4.5),
    /**
     * 未评估
     */
    UN_EVAL(5, "未评估",0);

    int tag;
    String description;
    double minScore;

    EvalRankEnum(int tag, String description,double minScore) {
        this.tag = tag;
        this.description = description;
        this.minScore = minScore;
    }


    public int getTag() {
        return tag;
    }

    public String getDescription() {
        return description;
    }

    public double getMinScore() {
        return minScore;
    }

    public static EvalRankEnum getEvalRank(BigDecimal evalScore) {
//        评分：done 随机评分 1 - /5 优：>= 4.5 良：>= 3.5 < 4.5 中：>= 2 < 3.5 差：< 2
//        考核等级 5 未评估 4 优 3 良 2 中 1 差
        if(evalScore == null){
            return null;
        }
        double f = evalScore.doubleValue();

        if (f < EvalRankEnum.FAIR.getMinScore()) {
            return EvalRankEnum.POOR;
        }
        if (f < EvalRankEnum.GOOD.getMinScore()) {
            return EvalRankEnum.FAIR;
        }
        if ( f < EvalRankEnum.EXCELLENT.getMinScore()) {
            return EvalRankEnum.GOOD;
        }
        if (f >= EXCELLENT.getMinScore()) {
            return EvalRankEnum.EXCELLENT;
        }
        return EvalRankEnum.UN_EVAL;
    }

    public static EvalRankEnum getEvalRank1(BigDecimal evalScore) {
//        评分：done 随机评分 1 - /5 优：>= 4.5 良：>= 3.5 < 4.5 中：>= 2 < 3.5 差：< 2
//        考核等级 5 未评估 4 优 3 良 2 中 1 差
        if (evalScore.compareTo(new BigDecimal(EvalRankEnum.FAIR.getMinScore())) == -1) {
            return EvalRankEnum.POOR;
        }
        if (evalScore.compareTo(new BigDecimal(EvalRankEnum.GOOD.getMinScore())) == -1) {
            return EvalRankEnum.FAIR;
        }
        if (evalScore.compareTo(new BigDecimal(EvalRankEnum.EXCELLENT.getMinScore())) == -1) {
            return EvalRankEnum.GOOD;
        }
        if (evalScore.compareTo(new BigDecimal(EvalRankEnum.EXCELLENT.getMinScore())) > -1) {
            return EvalRankEnum.EXCELLENT;
        }
        return EvalRankEnum.UN_EVAL;
    }

    public static void main(String[] args) {
        System.out.println(getEvalRank(new BigDecimal("3.5")));
        System.out.println(getEvalRank1(new BigDecimal("3.5")));
    }
}
