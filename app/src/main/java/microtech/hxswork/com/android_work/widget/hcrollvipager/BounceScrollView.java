package microtech.hxswork.com.android_work.widget.hcrollvipager;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;

/**
 * ScrollView����Ч����ʵ��
 */
public class BounceScrollView extends HorizontalScrollView {
	private View inner;
	private float x;
	private Rect normal = new Rect();
	private boolean isCount = false;
	private RotatImageView  mRotatImageView;
	
	public BounceScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	/***
	 * ���� XML ������ͼ�������.�ú�����������ͼ�������ã�����������ͼ�����֮��. ��ʹ���า���� onFinishInflate
	 * ������ҲӦ�õ��ø���ķ�����ʹ�÷�������ִ��.
	 */
	@Override
	protected void onFinishInflate() {
		if (getChildCount() > 0) {
			inner = getChildAt(0);
		}
	}
	
	//�ֶ���Ҫ���ù���λ��
	public void setScrolledTo(int position, float positionOffset) {
		this.scrollTo(position,(int) positionOffset);
	}

	 //����touch
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (inner != null) {
			commOnTouchEvent(ev);
		}

		return super.onTouchEvent(ev);
	}

	//�����¼�
	public void commOnTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			break;
		case MotionEvent.ACTION_UP:
			// ��ָ�ɿ�.
			if (isNeedAnimation()) {
				animation();
				isCount = false;
			}
			break;
		/***
		 * �ų�����һ���ƶ����㣬��Ϊ��һ���޷���֪y���꣬ ��MotionEvent.ACTION_DOWN�л�ȡ������
		 * ��Ϊ��ʱ��MyScrollView��touch�¼����ݵ�����LIstView�ĺ���item����.���Դӵڶ��μ��㿪ʼ.
		 * Ȼ������ҲҪ���г�ʼ�������ǵ�һ���ƶ���ʱ���û��������0. ֮���¼׼ȷ�˾�����ִ��.
		 */
		case MotionEvent.ACTION_MOVE:
			final float preX = x;// ����ʱ��y����
			float nowX = ev.getX();// ʱʱy����
			int deltaX = (int) (preX - nowX);// ��������
			if (!isCount) {
				deltaX = 0; // ������Ҫ��0.
			}

			x = nowX;
			// �����������ϻ�������ʱ�Ͳ����ٹ�������ʱ�ƶ�����
			if (isNeedMove()) {
				// ��ʼ��ͷ������
				if (normal.isEmpty()) {
					// ���������Ĳ���λ��
					normal.set(inner.getLeft(), inner.getTop(),
							inner.getRight(), inner.getBottom());
				}
				// �ƶ�����
				inner.layout(inner.getLeft() - deltaX / 4, inner.getTop(),
						inner.getRight()  - deltaX / 4, inner.getBottom());
				
				//ͼƬ�Ӻ���ת���������Ҫ���ֱ��ɾ�˾���
				if (mRotatImageView != null) {
					mRotatImageView.setRotationLeft();
				}
			}
			isCount = true;
			break;

		default:
			break;
		}
	}

	//��������
	public void animation() {
		// �����ƶ�����
		TranslateAnimation ta = new TranslateAnimation(0, 0, inner.getTop(),
				normal.top);
		ta.setDuration(200);
		inner.startAnimation(ta);
		// ���ûص������Ĳ���λ��
		inner.layout(normal.left, normal.top, normal.right, normal.bottom);
		normal.setEmpty();

	}
	
	//����ͼƬ�Ӻ���ת
	public void setRotatImageView(RotatImageView rotatImageView){
		this.mRotatImageView = rotatImageView;
	}

	// �Ƿ���Ҫ��������
	public boolean isNeedAnimation() {
		return !normal.isEmpty();
	}

	/***
	 * �Ƿ���Ҫ�ƶ����� inner.getMeasuredHeight():��ȡ���ǿؼ����ܸ߶�
	 * getHeight()����ȡ������Ļ�ĸ߶�
	 */
	public boolean isNeedMove() {
		int offset = inner.getMeasuredWidth() - getWidth();
		int scrollX = getScrollX();
		
		// 0�Ƕ�������
		//�ǵײ���������    
		if (scrollX == 0  || scrollX == offset) {
			return true;
		}
		return false;
	}

}
