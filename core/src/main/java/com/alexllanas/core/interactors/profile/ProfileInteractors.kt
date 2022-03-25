package com.alexllanas.core.interactors.profile

import com.alexllanas.core.interactors.common.*

data class ProfileInteractors(
    val logout: Logout,
    val getUser: GetUser,
    val getFollowers: GetFollowers,
    val getFollowing: GetFollowing,
    val changePassword: ChangePassword,
    val changeName: ChangeName,
    val changeBio: ChangeBio,
    val changeLocation: ChangeLocation,
    val changeAvatar: ChangeAvatar,
    val followUser: FollowUser,
    val unfollowUser: UnfollowUser,
    val likeTrack: LikeTrack,
    val unlikeTrack: UnlikeTrack,
)