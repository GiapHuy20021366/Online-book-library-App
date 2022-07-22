package com.example.library2.sever;

import com.example.library2.dataSend.*;
import com.example.library2.entity.Book;
import com.example.library2.sqlQuery.*;
import com.example.library2.utilities.Account;
import com.example.library2.utilities.MessageType;

import java.io.ObjectOutputStream;
import java.net.Socket;

public class HandRequest {
    public static void executeRequest(Request request, ObjectOutputStream o, Socket socket) {
        try {
            switch (request.type) {
                case BOOK_DATA_REQUEST -> {
                    o.writeObject(GetBookQuery.getDataBookById(((BookDataRequest) request).id));
                }
                case LIST_DATA_BOOK_REQUEST -> {
                    ListBookDataRequest list = (ListBookDataRequest) request;
                    o.writeObject(GetBookQuery.getDataBookById(list.fromId, list.toId));
                }
                case LOGIN -> {
                    LoginRequest loginRequest = (LoginRequest) request;
                    MessageType type = LoginQuery.checkAcc(loginRequest.name, loginRequest.pass);
                    o.writeObject(type);
                    if (type == MessageType.SUCCESS) {
                        MainSever.getInstance().updateAcc(socket, loginRequest.name);
                    }
                }
                case REGIS -> {
                    RegisNewAccountRequest regis = (RegisNewAccountRequest) request;
                    if (LoginQuery.checkAcc(regis.acc, "Pass Check Any") == MessageType.ERROR_PASSWORD) {
                        o.writeObject(MessageType.ACCOUNT_EXIST);
                        return;
                    }
                    if (CheckNewAccount.checkEmail(regis.email) == MessageType.EMAIL_EXIST) {
                        o.writeObject(MessageType.EMAIL_EXIST);
                        return;
                    }
                    if (CheckNewAccount.checkPhone(regis.phone) == MessageType.PHONE_EXIST) {
                        o.writeObject(MessageType.PHONE_EXIST);
                        return;
                    }
                    o.writeObject(CheckNewAccount.regisNewAcc(regis));
                }
                case GET_BOOK_FIND -> {
                    o.writeObject(FindBookSql.getBookLike(((GetBookFindRequest) request).s));
                }
                case GET_ALL_BOOK_DATA_FIND -> {
                    o.writeObject(FindBookSql.getAllBookAsFind(((GetBookDataFindRequest) request).s));
                }
                case RANK_BOOK -> {
                    o.writeObject(GetRankBook.getRank());
                }
                case NEW_BOOK_REQUEST -> {
                    o.writeObject(NewBookSql.getNewBooks());
                }
                case LIKE_REQUEST -> {
                    LikeRequest likeRequest = (LikeRequest) request;
                    o.writeObject(LikeQuery.like(likeRequest.bookID, likeRequest.account));
                }
                case HAD_LIKE_REQUEST -> {
                    LikeRequest likeRequest = (LikeRequest) request;
                    o.writeObject(LikeQuery.isLiked(likeRequest.bookID, likeRequest.account));
                }
                case DISLIKE_REQUEST -> {
                    DisLikeRequest disLikeRequest = (DisLikeRequest) request;
                    o.writeObject(LikeQuery.dislike(disLikeRequest.bookID, disLikeRequest.account));
                }
                case CHECK_ORDER -> {
                    CheckOrderRequest checkOrderRequest = (CheckOrderRequest) request;
                    o.writeObject(BorrowQuery.isOrdered(checkOrderRequest.bookID, checkOrderRequest.account));
                }
                case ORDER_BOOK -> {
                    OrderRequest orderRequest = (OrderRequest) request;
                    o.writeObject(BorrowQuery.order(orderRequest.bookID, orderRequest.account));
                }
                case REMOVE_ORDER -> {
                    RemoveOrderRequest removeOrderRequest = (RemoveOrderRequest) request;
                    o.writeObject(BorrowQuery.removeOrder(removeOrderRequest.bookID, removeOrderRequest.account));
                }
                case LOG_OUT -> {
                    String accName = MainSever.getInstance().getAccName(socket);
                    if (accName.equals(((LogOutRequest) request).name)) {
                        o.writeObject(MessageType.SUCCESS);
                        MainSever.getInstance().logOut(socket);
                    } else {
                        o.writeObject(MessageType.ERROR_NAME);
                    }
                }
                case LIKED_BOOK_REQUEST -> {
                    LikedBookRequest likedBookRequest = (LikedBookRequest) request;
                    o.writeObject(LikedBookQuery.getLikedBooks(likedBookRequest.account));
                }
                case BORROWED_BOOK_REQUEST -> {
                    BorrowedBookRequest borrowedBookRequest = (BorrowedBookRequest) request;
                    o.writeObject(BorrowQuery.getBorrowedBooks(borrowedBookRequest.account));
                }
                case ORDERED_BOOK_REQUEST -> {
                    OrderedBookRequest orderedBookRequest = (OrderedBookRequest) request;
                    o.writeObject(BorrowQuery.getOrderedBooks(orderedBookRequest.account));
                }
                case MANAGE_DATA_ORDER -> {
                    OrderedManageRequest manageRequest = (OrderedManageRequest) request;
                    o.writeObject(ManageOrderSql.getAllPreOrder(manageRequest.account));
                }
                case MANAGE_DATA_BORROWED -> {
                    BorrowedManageRequest manageRequest = (BorrowedManageRequest) request;
                    o.writeObject(ManageBorrowSql.getAllBorrowed(manageRequest.account));
                }
                case MARK_BORROW -> {
                    MarkBorrowRequest mark = (MarkBorrowRequest) request;
                    o.writeObject(MarkBookSql.markAsBorrow(mark));
                }
                case MARK_RETURN -> {
                    MarkReturn mark = (MarkReturn) request;
                    o.writeObject(MarkBookSql.markAsReturn(mark));
                }
                case GET_POS -> {
                    GetPosRequest getPosRequest = (GetPosRequest) request;
                    String pos = PosCheckSql.getPos(getPosRequest.account);
                    if (pos ==  null) {
                        o.writeObject(MessageType.NO_POS);
                        return;
                    }
                    switch (pos) {
                        case "EMPLOYEE" -> o.writeObject(MessageType.EMPLOYEE);
                        case "MANAGEMENT" -> o.writeObject(MessageType.MANAGEMENT);
                    }
                }
                case GET_CONNECTS -> {
                    GetConnects connects = (GetConnects) request;
                    o.writeObject(GetConnect.connects(connects.account));
                }
                case DISCONNECT_SOCKET -> {
                    DisconnectSocketRequest socketRequest = (DisconnectSocketRequest) request;
                    o.writeObject(DisconnectQuery.disconnect(socketRequest.socketData));
                }
                case CLOSE_SEVER -> {
                    MainSever.getInstance().shutdown();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
