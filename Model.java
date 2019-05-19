class Model
{
    private int fieldWidth;
    private int fieldHeight;
    private int minesPercent;
    private int minesQuantity;

    public Model( int width, int height, int mines )
    {
        fieldWidth = width;
        fieldHeight = height;
        minesPercent = mines;
    }

    int setWidth( int width )
    {
        if( ( fieldWidth >= 50 && width == -1 ) || ( fieldWidth <= 4 && width == -2 ) || width == 0 )
            return fieldWidth;
        if( width == -1 )
            return ++fieldWidth;
        if( width == -2 )
            return --fieldWidth;
        fieldWidth = width;
        return fieldWidth;
    }

    int setHeight( int height )
    {
        if( ( fieldHeight >= 50 && height == -1 ) || ( fieldHeight <= 4 && height == -2 ) || height == 0 )
            return fieldHeight;
        if( height == -1 )
            return ++fieldHeight;
        if( height == -2 )
            return --fieldHeight;
        fieldHeight = height;
        return fieldHeight;
    }

    int setMines( int mines )
    {
        if( ( minesPercent >= 80 && mines == -1 ) || ( minesPercent <= 10 && mines == -2 ) || mines == 0 )
            return minesPercent;
        if( mines == -1 )
            return ++minesPercent;
        if( mines == -2 )
            return --minesPercent;
        minesPercent = mines;
        return minesPercent;
    }

    void countMinesQuantity()
    {
        minesQuantity = minesPercent * fieldHeight * fieldWidth / 100;
    }
}