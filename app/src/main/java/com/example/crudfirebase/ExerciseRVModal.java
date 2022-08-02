package com.example.crudfirebase;

import android.os.Parcel;
import android.os.Parcelable;

public class ExerciseRVModal implements Parcelable
{

    private String exerciseName;
    private String exerciseDescription;
    private String tutorialPrice;
    private String bestChosen;
    private String exerciseImg;
    private String exerciseLink;
    private String exerciseID;

    protected ExerciseRVModal(Parcel in) {
        exerciseName = in.readString();
        exerciseDescription = in.readString();
        tutorialPrice = in.readString();
        bestChosen = in.readString();
        exerciseImg = in.readString();
        exerciseLink = in.readString();
        exerciseID = in.readString();
    }

    public static final Creator<ExerciseRVModal> CREATOR = new Creator<ExerciseRVModal>() {
        @Override
        public ExerciseRVModal createFromParcel(Parcel in) {
            return new ExerciseRVModal(in);
        }

        @Override
        public ExerciseRVModal[] newArray(int size) {
            return new ExerciseRVModal[size];
        }
    };

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getExerciseDescription() {
        return exerciseDescription;
    }

    public void setExerciseDescription(String exerciseDescription) {
        this.exerciseDescription = exerciseDescription;
    }

    public String getTutorialPrice() {
        return tutorialPrice;
    }

    public void setTutorialPrice(String tutorialPrice) {
        this.tutorialPrice = tutorialPrice;
    }

    public String getBestChosen() {
        return bestChosen;
    }

    public void setBestChosen(String bestChosen) {
        this.bestChosen = bestChosen;
    }

    public String getExerciseImg() {
        return exerciseImg;
    }

    public void setExerciseImg(String exerciseImg) {
        this.exerciseImg = exerciseImg;
    }

    public String getExerciseLink() {
        return exerciseLink;
    }

    public void setExerciseLink(String exerciseLink) {
        this.exerciseLink = exerciseLink;
    }

    public String getExerciseID() {
        return exerciseID;
    }

    public void setExerciseID(String exerciseID) {
        this.exerciseID = exerciseID;
    }

    public ExerciseRVModal(){

    }

    public ExerciseRVModal(String exerciseName, String exerciseDescription, String tutorialPrice, String bestChosen, String exerciseImg, String exerciseLink, String exerciseID) {
        this.exerciseName = exerciseName;
        this.exerciseDescription = exerciseDescription;
        this.tutorialPrice = tutorialPrice;
        this.bestChosen = bestChosen;
        this.exerciseImg = exerciseImg;
        this.exerciseLink = exerciseLink;
        this.exerciseID = exerciseID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(exerciseName);
        parcel.writeString(exerciseDescription);
        parcel.writeString(tutorialPrice);
        parcel.writeString(bestChosen);
        parcel.writeString(exerciseImg);
        parcel.writeString(exerciseLink);
        parcel.writeString(exerciseID);
    }
}
