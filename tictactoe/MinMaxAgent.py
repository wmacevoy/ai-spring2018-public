import random
from Const import Const
from Move import Move
from Game import Game

class MinMaxAgent:
    def __init__(self, side):
        if side != Const.MARK_O and side != Const.MARK_X:
            raise ValueError("side must be MARK_X or MARK_O")
        self.side = side

    def value(self,game):
        ans = None
        state = game.getState()
        if state == Const.STATE_WIN_O:
            if self.side == Const.MARK_O: ans = 1
            else: ans = -1
        elif state == Const.STATE_WIN_X:
            if self.side == Const.MARK_X: ans = 1
            else: ans = -1
        elif state == Const.STATE_DRAW:
            return 0

        if ans != None: return ans

        iside = 0
        if self.side == Const.MARK_O: iside = 1
        else: iside = -1

        iturn = 0
        if state == Const.STATE_TURN_O: iturn = 1
        else: iturn = -1

        myTurn = (iside == iturn)

        for move in game.getMoves():
            move.play(game)
            moveValue=self.value(game)
            move.unplay(game)
            if ans == None:
                ans = moveValue
            else:
                if myTurn:
                   ans = max(ans,moveValue)
                else:
                   ans = min(ans,moveValue)

        return ans

    def move(self,game):
        maxValue=self.value(game)
        playable = []
        for move in game.getMoves():
            move.play(game)
            moveValue=self.value(game)
            move.unplay(game)
            if moveValue == maxValue:
                playable.append(move)
        spot=random.randint(0,len(playable)-1)
        return playable[spot]
